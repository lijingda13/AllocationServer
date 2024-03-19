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
    useDataProvider
} from "react-admin";
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import '../share/styles.css';
import { useNotify, useRedirect } from 'react-admin';
import {  useState } from "react";
import '../mock/mock.js';
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
            notify(res.msg, {type: 'success'});
            // 自动刷新列表
        });
    }
    return (
        <Stack>
            <Button disabled={record.registerStatus} size="small" variant="outlined" onClick={handleRegisterInterest}>Register Interest</Button>
        </Stack>
    )
};

// customize top-right action
const ListActions = () => {
    const role = localStorage.getItem("role");
    return (
        <TopToolbar>
            {role === 'staff' ? <CreateButton/> : null}
            <ExportButton/>
        </TopToolbar>
    );
}

// Project List
export const PostList = () => {
    const role = localStorage.getItem("role");
    const notify = useNotify();
    const redirect = useRedirect();

    const onError = (error:any) => {
        notify(`Could not load list: server error`);
        redirect('/');
    };
    return (
    <List actions={<ListActions/>} pagination={false} queryOptions={{ onError,  meta: { role } }}>
        <Datagrid bulkActionButtons={false} rowClick="edit">
            <TextField source="id" sortable={false} />
            <TextField source="title" sortable={false}/>
            <TextField source="description" sortable={false} />
            {role === 'staff'?
            '':
            <TextField source="staff" sortable={false}/>
            }
            <TextField label="Assigned Student" source="student" sortable={false}/>
            <FunctionField
                label="Project Status"
                render={(record: { status: any; }) => (record.status? 'Available':'Unavailable')}
            />
            <FunctionField
                sx={{color: "blue"}}
                label="Interest Students"
                render={(record: { interestStudents: string | any[]; }) => `${record.interestStudents.length}`}
            />
            {role === 'staff'?
            '':
            <TextField source="registerInterestStatus" sortable={false}/>
            }
            {(role === 'staff') ? 
            null: 
            <Mybutton />}
        </Datagrid>
    </List>
);}

// cutomize title
const PostTitle = () => {
    const record = useRecordContext();
    return <span>Project {record ? `"${record.title}"`: ''}</span>
}

/* in order to get the edit data, we should use the intermediate component */
const CustomizeForm = (props:any) => {
    const role = localStorage.getItem("role");
    const record = useRecordContext(props);
    const {getAssigendStudent} = props;
    const handleChange = (e:any) => {
        getAssigendStudent(e.target.value)
        setStudentID(e.target.value);
    }
    const [studentId, setStudentID] = useState();
    return (
        <>
            <SimpleForm toolbar={<PostEditToolbar/>}>
                <TextInput  sx={{width:"250px"}} source="title" readOnly/>
                <TextInput  sx={{width:"250px"}} source="staff" readOnly/>
                {record.student ? <TextInput  sx={{width:"250px"}} source="student" readOnly/> : null}
                <TextInput  sx={{width:"250px"}} source="description" multiline rows={5} readOnly/>
                {(role==='student'||record.student) ? 
                    null : 
                    <SelectInput sx={{width:"250px"}} onChange={handleChange} source="assignInterestStudents" validate={required()} choices={record["interestStudents"]} readOnly={record["student"]?true:false}/>}
            </SimpleForm>
        </>
    );
};

// assign student 
export const PostEdit = () => {
    const [assignStudentId, setAssignStudentId] = useState();
    const getAssigendStudent = (data: any) => {
        setAssignStudentId(data)
    }
    
    return( 
    <Edit title={<PostTitle/>} mutationOptions={{ meta: {student_id: assignStudentId} }} resource="projects">
        <CustomizeForm getAssigendStudent={getAssigendStudent}/>  
    </Edit>
);}

// customize edit save btn
const PostEditToolbar = (props:any) => {
    const role = localStorage.getItem("role");
    const redirect = useRedirect();
    const record = useRecordContext(props);
    const goBack = () => {
        redirect("/projects")
    }
    return(

    <Toolbar sx={{display: "flex",justifyContent:"space-between"}}>
            {role==='staff' ?
            <SaveButton label="save" disabled={record.status?false:true} />:
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
