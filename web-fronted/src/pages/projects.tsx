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

} from "react-admin";

import { Link } from "@mui/material";
import StarBorderIcon from '@mui/icons-material/StarBorder';
import StarIcon from '@mui/icons-material/Star';
import "./styles.css";
import { dataProvider } from "./dataProvider";

// interest project
const Mybutton = () => {
    const record = useRecordContext();
    return record ? (
        <Link>
            <div className="customize-collect-bt">
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

// Project List
export const PostList = () => (
    <List filters={postFilters} pagination={false} >
        <Datagrid bulkActionButtons={false} rowClick="edit">
            <TextField source="id" sortable={false} />
            <TextField source="title" sortable={false}/>
            <TextField source="description" sortable={false}/>
            <TextField source="staff" sortable={false}/>
            <TextField source="student" sortable={false}/>
            <BooleanField source="status" sortable={false}/>
            <TextField source="interestStudents" sortable={false}/>
            <Mybutton />
        </Datagrid>
    </List>
);

// cutomize title
const PostTitle = () => {
    const record = useRecordContext();
    return <span>Project {record ? `"${record.title}"`: ''}</span>
}

// assign student 
export const PostEdit = () => {
    return ( 
        <Edit title={<PostTitle/>}>
            <SimpleForm toolbar={<PostCreateToolbar />}>
                <TextInput source="title" disabled/>
                <TextInput source="staff" disabled/>
                <TextInput source="student" disabled/>
                <TextInput source="description" multiline rows={5} disabled/>
                <SelectInput source="assignStudent" validate={required()} choices={[
                    { id: '1', name: 'Tom' },
                    { id: '2', name: 'Joe' },
                    { id: '3', name: 'SS' },
                ]} />
            </SimpleForm>
        </Edit>
    );
}
// customize save btn
const PostCreateToolbar = () => (<Toolbar><SaveButton label="save"/></Toolbar>);

// create project
export const PostCreate = () => (
    <Create>
        <SimpleForm>
            <TextInput source="title" validate={required()} />
            <TextInput source="description" multiline rows={5} validate={required()} />
        </SimpleForm>
    </Create>
);






