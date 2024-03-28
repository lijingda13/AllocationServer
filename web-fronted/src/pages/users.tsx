import { SimpleForm, TextInput, required, useDataProvider, useNotify } from "react-admin";
import { useEffect, useState } from "react";

export const UserList = () => {
    const notify = useNotify();
    const [defaultValue, setDefaultValue] = useState(null);
    const [password, setPassword] = useState();
    const [email, setEmail] = useState();
    const dataProvider = useDataProvider();
    useEffect(() => {
        dataProvider.getUser().then((res: any) => {
            setDefaultValue(res);
        })
    }, []); 

    const postSave = () => {
        dataProvider.saveUser({password, email}).then((res: any) => {
            notify("Update successfully", {type: "success"});
        }).catch((e: any) => {
            notify(e, { type: 'error' });
        })
    }
    
    return (
        <SimpleForm defaultValues={defaultValue} onSubmit={postSave}>
            <TextInput sx={{width:"250px"}} source="username" disabled  validate={required()}/>
            <TextInput sx={{width:"250px"}} source="firstName" disabled  validate={required()}/>
            <TextInput sx={{width:"250px"}} source="lastName" disabled  validate={required()}/>
            <TextInput sx={{width:"250px"}} source="role" disabled  validate={required()}/>
            <TextInput sx={{width:"250px"}} source="password"  validate={required()} onChange={e => setPassword(e.target.value)}/>
            <TextInput sx={{width:"250px"}} source="email"  validate={required()} onChange={e => setEmail(e.target.value)}/>
        </SimpleForm>    
    );
}