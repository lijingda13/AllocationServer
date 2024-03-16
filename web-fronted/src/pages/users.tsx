import { SimpleForm, TextInput } from "react-admin";
export const UserList = () => (
    <SimpleForm>
        <TextInput source="username" disabled required/>
        <TextInput source="firstname" required/>
        <TextInput source="lastname" required/>
    </SimpleForm>    
);