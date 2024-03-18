import { List, Datagrid, TextField,
    Edit,
    SimpleForm,
    TextInput,
    Create,
    useRecordContext,
    BooleanField,
    Toolbar,
    SaveButton,
    required,
    SelectInput,
    CheckboxGroupInput,
    TopToolbar,
    ExportButton,
    CreateButton,
    FilterButton,
    SelectColumnsButton,
    GetListParams,
    FunctionField,
    ReferenceInput,
    useEditContext,
    FormDataConsumer,
    Form,
    maxLength,

} from "react-admin";

import { Link } from "@mui/material";
import StarBorderIcon from '@mui/icons-material/StarBorder';
import StarIcon from '@mui/icons-material/Star';
import "./styles.css";
import { dataProvider } from "./dataProvider";
import { useNotify, useRedirect } from 'react-admin';
import { findSourceMap } from "module";
import { FieldValues } from "react-hook-form";

// interest project
const Mybutton = () => {
    const record = useRecordContext();
    const handleCollect = (event:any) => {
        event.preventDefault();
        event.stopPropagation();
        console.log('shoucangle')
        // handle interest
        const params:GetListParams = { 
            pagination: { page: 1, perPage: 10 },
            sort: { field: 'published_at', order: 'DESC' },
            filter: 2
        }
        dataProvider.getList('posts', params)

    }
    return record ? (
        <Link>
            <div className="customize-collect-bt" onClick={handleCollect}>
                {
                    record.status ? 
                    <StarIcon  sx={{ fontSize: 15, ml: 1 }} />
                    : 
                    <StarBorderIcon className="customize-collect-bt" sx={{ fontSize: 15, ml: 1 }} />
                }
            </div> 
        </Link>
    ) : null;
};

// project filter
const postFilters = [
    <CheckboxGroupInput alwaysOn source="Project" choices={[
        { id: '1', name: 'My Project' },
    ]} />
];

// customize top-right action
const ListActions = () => (
    <TopToolbar>
        <CreateButton/>
        <ExportButton/>
    </TopToolbar>
);

const LongFiled = () => {
    
}


// Project List
export const PostList = () => {
    const notify = useNotify();
    const redirect = useRedirect();

    const onError = (error:any) => {
        notify(`Could not load list: server error`);
        redirect('/');
    };
    
    return (
    <List actions={<ListActions/>} pagination={false} queryOptions={{ onError }}>
        <Datagrid bulkActionButtons={false} rowClick="edit">
            <TextField source="id" sortable={false} />
            <TextField source="title" sortable={false}/>
            <TextField source="description" sortable={false} />
            <TextField source="staff" sortable={false}/>
            <TextField source="student" sortable={false}/>
            <BooleanField source="status" sortable={false}/>
            <FunctionField
                sx={{color: "blue"}}
                label="Interest Students"
                render={(record: { interestStudents: string | any[]; }) => `${record.interestStudents.length}`}
            />
            <Mybutton />
        </Datagrid>
    </List>
);}



let choicesStudents: any[] | undefined = [];
// cutomize title
const PostTitle = () => {
    const record = useRecordContext();
    choicesStudents = record ? record.choicesStudents: [];
    console.log(record);
    return <span>Project {record ? `"${record.title}"`: ''}</span>
}

/* in order to get the edit data, we should use the intermediate component */
const CustomizeForm = (props:any) => {
    const record = useRecordContext(props);
    console.log({ record });
    return (
        <>
            <SimpleForm {...props} toolbar={<PostCreateToolbar />}>
                <TextInput source="title" readOnly/>
                <TextInput source="staff" readOnly/>
                {record.student ? <TextInput source="student" readOnly/> : null}
                <TextInput source="description" multiline rows={5} readOnly/>
                {record.student ? null : <SelectInput source="assignStudent" validate={required()} choices={record["interestStudents"]} readOnly={record["student"]?true:false}/>}
            </SimpleForm>
        </>
    );
};

// assign student 
export const PostEdit = () => ( 
    <Edit title={<PostTitle/>} mutationOptions={{ meta: { foo: 'bar' } }} resource="projects" queryOptions={{ meta: { foo: 'bar' } }}>
        <CustomizeForm/>  
    </Edit>
);





// customize save btn
const PostCreateToolbar = (props:any) => {

    const record = useRecordContext(props);
    console.log(record)

    return(

    <Toolbar>
        <SaveButton label="save" disabled={record.status?false:true}/>
        
    </Toolbar>);}

// create project
export const PostCreate = () => (
    <Create>
        <SimpleForm>
            <TextInput source="title" validate={required()} />
            <TextInput source="description" multiline rows={5} validate={required()}/>
        </SimpleForm>
    </Create>
);






