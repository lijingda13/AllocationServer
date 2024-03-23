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
    WrapperField,
    useGetOne
} from "react-admin";
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import Tooltip from '@mui/material/Tooltip';
import '../share/styles.css';
import { useNotify, useRedirect } from 'react-admin';
import { useState } from "react";
import { useNavigate } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import '../mock/mock.js';
import { dataProvider1 } from "./dataProvider";
// interest project
const Mybutton = () => {
    const notify = useNotify();
    const record = useRecordContext();
    const dataProvider = useDataProvider();
    const handleRegisterInterest = (event:any) => {
        event.preventDefault();
        event.stopPropagation();
        // handle interest
        dataProvider.registerInterest(record.id).then((res:any) => {
            console.log(res)
            // notify(res.msg, {type: 'success'});
            // 自动刷新列表
            // dataProvider1.getList('projects', ) // bug：需要检查！！
        }).catch((e: any) => {
            console.log(e);
        });
    }
    const handleCancelInterest = (event:any) => {
        event.preventDefault();
        event.stopPropagation();
        // handle interest
        dataProvider.cancelInterest(record.id).then((res:any) => {
            console.log(res)
            // notify(res.msg, {type: 'success'});
            // 自动刷新列表
        }).catch((e: any) => {
            console.log(e);
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

    const viewProject = (event:any) => {
        navigate('/projects/edit', { state: Object.assign({record}, {action: 'view'}) });

    }
    const assignStudent = (event: any) => {
        event.preventDefault();
        event.stopPropagation();
        navigate('/projects/edit', { state: Object.assign(record, {action: 'assign'}) });
    }
    return (
        <Stack>
            {/* <Button size="small" variant="outlined" onClick={viewProject}>View Detail</Button> */}
            <Button disabled={!!record.assignedStudent} size="small" variant="outlined" onClick={assignStudent}>Assign Student</Button>
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
        console.log(record);
        navigate('/projects/edit', { state: Object.assign(record, {action: 'view'}) })
      
        // redirect('/projects/edit', 'posts', 1, {}, { record: record });
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
                // <TextField source="staff." sortable={false}/>
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
    console.log(location.state);
    const role = localStorage.getItem("role");
    const record = useRecordContext(props);
    const {getAssigendStudent} = props;
    const handleChange = (e:any) => {
        getAssigendStudent(e.target.value)
        setStudentID(e.target.value);
    }
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
                        optionText="firstName"
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
    const [assignStudentId, setAssignStudentId] = useState();
    const getAssigendStudent = (data: any) => {
        setAssignStudentId(data)
    }
    return( 
    <Edit title={<PostTitle/>} mutationOptions={{ meta: {studentId: assignStudentId, projectId: location.state.id} }} resource="projects">
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
            <SaveButton label="save" disabled={postDefaultValue.status} />:
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
