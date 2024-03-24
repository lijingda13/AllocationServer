import { List, Datagrid, TextField,
    Edit,
    SimpleForm,
    TextInput,
    Create,
    useRecordContext,
    Toolbar,
    SaveButton,
    required,
    SelectInput,
    TopToolbar,
    ExportButton,
    CreateButton,
    FunctionField,
    useDataProvider,
} from "react-admin";
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import Tooltip from '@mui/material/Tooltip';
import '../share/styles.css';
import { useNotify, useRedirect } from 'react-admin';
import { useState } from "react";
import { useNavigate } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import { useRefresh } from 'react-admin';

const Mybutton = () => {
    const notify = useNotify();
    const record = useRecordContext();
    const dataProvider = useDataProvider();
    const refresh = useRefresh();
    const handleRegisterInterest = (event:any) => {
        event.preventDefault();
        event.stopPropagation();
        // handle interest
        dataProvider.registerInterest(record.id).then((res:any) => {
            notify('Register successfully', {type: 'success'});
            refresh();
        }).catch((e: any) => {
            notify('Failed to register interest', {type: 'error'});
        });
    }
    const handleCancelInterest = (event:any) => {
        event.preventDefault();
        event.stopPropagation();
        // handle interest
        dataProvider.cancelInterest(record.id).then((res:any) => {
            notify('Cancel successfully', {type: 'success'});
            refresh();
        }).catch((e: any) => {
            notify('Failed to cancel interest', {type: 'error'});
        });
    }
    return (
        <Stack>
            {
                record.registerStatus ? 
                <Button size="small" variant="contained" onClick={handleCancelInterest}>Cancel Interest</Button>:
                <Button size="small" variant="outlined" onClick={handleRegisterInterest}>Register Interest</Button>
            }
        </Stack>
    )
};

const Staffbutton = () => {
    const record = useRecordContext();
    const navigate = useNavigate();
    const dataProvider = useDataProvider();
    const notify = useNotify();
    const refresh = useRefresh();
    const assignStudent = (event: any) => {
        event.preventDefault();
        event.stopPropagation();
        navigate('/projects/edit', { state: Object.assign(record, {action: 'assign'}) });
    }
    const deleteProject = (event: any) => {
        event.preventDefault();
        event.stopPropagation();
        dataProvider.deleteProject(record.id).then((res:any) => {
            notify('delete successfully', {type: 'success'});
            refresh();
        }).catch((e: any) => {
            notify('Failed to delete', {type: 'error'});
        });
    }
    return (
        <Stack sx={{flexDirection: "row"}}>
            <Button sx={{marginRight: "10px"}} disabled={!(!record.assignedStudent && record.interestStudents?.length)} size="small" variant="outlined" onClick={assignStudent}>Assign Student</Button>
            <Button size="small" variant="outlined" onClick={deleteProject} disabled={!!record.assignedStudent}>Delete</Button>
        </Stack>
    )
};

// customize top-right action
const ListActions = () => {
    const role = localStorage.getItem("role");
    return (
        <TopToolbar>
            {role === 'STAFF' ? <CreateButton/> : null}
            <ExportButton/>
        </TopToolbar>
    );
}

// Project List
export const PostList = () => {
    const role = localStorage.getItem("role");
    const notify = useNotify();
    const redirect = useRedirect();
    const dataProvider = useDataProvider();

    const onError = (error:any) => {
        notify(`Could not load list: server error`);
        redirect('/');
    };
    const navigate = useNavigate();

    const transferRowData = (record: any) => {
        navigate('/projects/edit', { state: Object.assign(record, {action: 'view'}) });
    }
    return (
        
    <List filters={role === 'STUDENT' ? postFilters: undefined} actions={<ListActions/>} pagination={false} queryOptions={{ onError,  meta: { role } }} >
        <Datagrid bulkActionButtons={false} rowClick={((id, resource, record) => {
            transferRowData(record);
            return false;})}>
            <TextField source="id" sortable={false} />
            <TextField source="title" sortable={false}/>
            <FunctionField
                label="Description"
                render={(record: any) => (
                    <Tooltip title={record?.description}>
                        <div style={{ width: "200px", overflow: "hidden", whiteSpace: "nowrap", textOverflow: "ellipsis" }}>{record?.description}</div>
                    </Tooltip>
                )}
            />
            {
                role === 'STAFF'?
                '':
                <FunctionField
                    label="Staff"
                    render={(record: any) => `${record.staff?.firstName} ${record.staff?.lastName}`}
                />
            }
            {
               role === 'STAFF'?
                <FunctionField
                    label="Assigned Student"
                    render={(record: any) => (record.assignedStudent ? `${record.assignedStudent.firstName} ${record.assignedStudent.lastName}` : null)}
                />:
                ''
            }
            
            <FunctionField
                label="Project Status"
                render={(record: { status: any; }) => (record.status? 'Unavailable' : 'Available')}
            />
            {
                role === 'STAFF'?
                <FunctionField
                    sx={{color: "blue"}}
                    label="Interest Students"
                    render={(record: any) => (
                        <Tooltip title="click to view detail">
                            <div style={{ width: "200px", overflow: "hidden", whiteSpace: "nowrap", textOverflow: "ellipsis" }}>{record.interestStudents?.length}</div>
                        </Tooltip>
                    )}
                /> :
                ''
            }
            
            {
                role === 'STAFF'?
                '':
                <FunctionField
                    label="Register Interest Status"
                    render={(record: any) => (record.registerInterest ? 'Registered' : 'Unregistered')}
                />
            }
            {
                role === 'STAFF' ? 
                <Staffbutton/>: 
                <Mybutton />
            }
        </Datagrid>
    </List>
);}

const postFilters = [
    <SelectInput value="1" alwaysOn source="category"  validate={required()} choices={[
        { id: '1', name: 'Available Projects' },
        { id: '2', name: 'My Projects' },
    ]} />
];

// cutomize title
const PostTitle = () => {
    const location = useLocation();
    const postDefaultValue = location.state;
    const record = useRecordContext();
    return <span>Project -- {postDefaultValue.title}</span>
}

/* in order to get the edit data, we should use the intermediate component */
const CustomizeForm = (props:any) => {
    const [studentId, setStudentID] = useState();
    // setStudentID(undefined);
    const location = useLocation();
    const postDefaultValue = location.state;
    const role = localStorage.getItem("role");
    const record = useRecordContext(props);
    const {getAssigendStudent} = props;
    const handleChange = (e:any) => {
        getAssigendStudent(e.target.value)
        setStudentID(e.target.value);
    }
    const optionRenderer = (choice: any) => `${choice.firstName} ${choice.lastName}`;
    return (
        <>
            <SimpleForm toolbar={<PostEditToolbar/>} defaultValues={postDefaultValue}>
                <TextInput  sx={{width:"250px"}} source="title" readOnly/>
                <TextInput  sx={{width:"250px"}} source="description" multiline rows={5} readOnly/>
                {
                    role == 'STUDENT' ?
                    <>
                        <TextInput  sx={{width:"250px"}} source="staff.firstName" readOnly/>
                        <TextInput  sx={{width:"250px"}} source="staff.lastName" readOnly/>
                        <TextInput  sx={{width:"250px"}} source="staff.email" readOnly/>
                    </>:
                    ''
                }
               
                {postDefaultValue.assignedStudent ? <TextInput  sx={{width:"250px"}} source="assignedStudent.firstName" readOnly/> : null}
                {postDefaultValue.assignedStudent ? <TextInput  sx={{width:"250px"}} source="assignedStudent.lastName" readOnly/> : null}
                {postDefaultValue.assignedStudent ? <TextInput  sx={{width:"250px"}} source="assignedStudent.email" readOnly/> : null}
                {(role==='STUDENT'||postDefaultValue.assignedStudent||!postDefaultValue.interestStudents?.length || postDefaultValue.action=='view') ? 
                    null : 
                    <SelectInput 
                        sx={{width:"250px"}} 
                        onChange={handleChange} 
                        source="assignInterestStudents" 
                        validate={required()} 
                        choices={postDefaultValue["interestStudents"]} 
                        readOnly={postDefaultValue["student"]?true:false}
                        optionText={optionRenderer}
                        optionValue="id"
                    />
                }
            </SimpleForm>
        </>
    );
};

// assign student 
export const PostEdit = (props: any) => {
    const location = useLocation();
    const [assignStudent, setAssignStudent] = useState();
    const getAssigendStudent = (id: any) => {
        const student = location.state.interestStudents.find((v: any) => v.id == id);
        setAssignStudent(student);

    }
    return( 
    <Edit  mutationMode="optimistic" title={<PostTitle/>} mutationOptions={{ meta: {student: assignStudent, projectId: location.state.id} }} resource="projects">
        <CustomizeForm  getAssigendStudent={getAssigendStudent}/>  
    </Edit>
);}

// customize edit save btn
const PostEditToolbar = (props:any) => {
    const location = useLocation();

    const postDefaultValue = location.state;
    const role = localStorage.getItem("role");
    const redirect = useRedirect();
    const record = useRecordContext(props);
    const goBack = () => {
        redirect("/projects")
    }
    return(

    <Toolbar sx={{display: "flex",justifyContent:"space-between"}}>
            {role==='STAFF' ?
            <SaveButton label="save" disabled={postDefaultValue.status}/>:
            null
            }
            <Stack>
                <Button variant="contained" onClick={goBack}>Go Back</Button>
            </Stack>
        
    </Toolbar>);
}

// create project
export const PostCreate = () => (
    <Create resource="/projects">
        <SimpleForm toolbar={<PostCreateToolbar/>}>
            <TextInput source="title" validate={required()} />
            <TextInput source="description" multiline rows={5} validate={required()}/>
        </SimpleForm>
    </Create>
);
// customize create save btn
const PostCreateToolbar = (props:any) => {
    const redirect = useRedirect();
    const record = useRecordContext(props);
    const goBack = () => {
        redirect("/projects");
    }
    return(

    <Toolbar sx={{display: "flex",justifyContent:"space-between"}}>
        
            <SaveButton label="save"/>
            <Stack>
                <Button variant="contained" onClick={goBack}>Go Back</Button>
            </Stack>
        
    </Toolbar>);
}
