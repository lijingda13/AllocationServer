import { SimpleForm, TextInput, required, useDataProvider, useNotify } from "react-admin";
import { useEffect, useState } from "react";

export const UserList = () => {
    const notify = useNotify();
    const [defaultValue, setDefaultValue] = useState(null);
    const [password, setPassword] = useState();
    const [email, setEmail] = useState();
    const dataProvider = useDataProvider();
    useEffect(() => {
        dataProvider.getUser().then((res: { data: any}) => {
            console.log(res.data);
            setDefaultValue(res.data);
        })
    }, []); 

    const postSave = () => {
        dataProvider.saveUser({password, email}).then((res: any) => {
            console.log(res);
            // 判断是否成功：
            notify("Update successfully", {type: "success"});
        }).catch(() => {
            notify("Failed to update information");
        })
    }
    
    return (
        <SimpleForm defaultValues={defaultValue} onSubmit={postSave}>
            <TextInput sx={{width:"250px"}} source="username" disabled  validate={required()}/>
            <TextInput sx={{width:"250px"}} source="firstname" disabled  validate={required()}/>
            <TextInput sx={{width:"250px"}} source="lastname" disabled  validate={required()}/>
            <TextInput sx={{width:"250px"}} source="role" disabled  validate={required()}/>
            <TextInput sx={{width:"250px"}} source="password"  validate={required()} onChange={e => setPassword(e.target.value)}/>
            <TextInput sx={{width:"250px"}} source="email"  validate={required()} onChange={e => setEmail(e.target.value)}/>
        </SimpleForm>    
    );
}