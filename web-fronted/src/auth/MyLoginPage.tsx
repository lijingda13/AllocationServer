import * as React from 'react';
import { useState } from 'react';
import { useLogin, useNotify, Notification, Login, fetchUtils, useRedirect } from 'react-admin';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Button from '@mui/material/Button';
import LoadingButton from '@mui/lab';

import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';

import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { BACKEND_URL } from '../share/env';
import { Url } from '../share/url';
const httpClient = fetchUtils.fetchJson;


const MyLoginPage = () => {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [role, setRole] = useState('');
    let [usernameStatus, setUsernameStatus] = useState(false);
    let [passwordStatus, setPasswordStatus] = useState(false);
    let [emailStatus, setEmailStatus] = useState(false);
    let [roleStatus, setRoleStatus] = useState(false);
    const [value, setValue] = useState(0);

    const [successMsg, setSuccessMsg] = useState(false);
    const login = useLogin();
    const notify = useNotify();
    const redirect = useRedirect();

    const handleSubmit = async  (e: {[x: string]: any; preventDefault: () => void; }) => {
        e.preventDefault();
        const action = e.target.id;

        if (!username.trim()) {
            setUsernameStatus(true);
            return;
        }
        setUsernameStatus(false);
        if (!password.trim()) {
            setPasswordStatus(true);
            return;
        }
        setPasswordStatus(false);
        if (!email.trim() && action === 'register') {
            setEmailStatus(true);
            return;
        }
        setEmailStatus(false);
        if (action === 'login') {
            console.log(123)
            // will call authProvider.login({ email, password })
            login({ username, password }).catch(() =>{
                return notify("Some of your information isn't correct. Please try again.", {type:"error"})
            });
        } else {
            const params = JSON.stringify({username, password, email, role});
            const result = await httpClient(BACKEND_URL + Url.users_post, {method: 'POST', body: params})
            .then((res:any) => {
                console.log('注册成功：',res);
                if (res.status === 201) {
                    notify(res.json.message, { type: 'success' });
                    setValue(0); // 去登录页签
                    setEmail('');
                } else {
                    notify(res.json.message, { type: 'error' });
                }
            }).catch(e => {
                notify("Registration failed", { type: 'error' });
            });
        }
    
    };

    // handle tab
    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };
    function a11yProps(index: number) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
    }

    return (
        <>
        <div className="login">
            <div className="login-main">
                <div className="login-main-icon">
                    <div className='login-main-icon-bg'><LockOutlinedIcon/></div>
                </div>
                <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab className='tab-btn' label="Login" {...a11yProps(0)} />
                    <Tab className='tab-btn' label="Register" {...a11yProps(1)} />
                </Tabs>
                <form onSubmit={handleSubmit}>
                    <div className="login-main-field">
                        {/* <BasicTextFields data-value={value}/> */}
                        <Box
                        component="form"
                        sx={{
                            '& .MuiTextField-root': { m: 1, width: '25ch' },
                            '& > :not(style)': { m: 1, width: '25ch' },
                        }}
                        
                        autoComplete="off"
                        >
                        <TextField error={usernameStatus} helperText={usernameStatus ? "username is required" : ''} className='customize-field' id="filled-basic1" label="Username" variant="filled" required onChange={e => {setUsername(e.target.value); setUsernameStatus(!e.target.value.trim())}}/><br/>
                        <TextField error={passwordStatus} helperText={passwordStatus ? "password is required" : ''} id="filled-basic2" label="Password" variant="filled" required  onChange={e => {setPassword(e.target.value); ; setPasswordStatus(!e.target.value.trim())}}/><br/>
                        {value==1? 
                            <>
                                <TextField error={emailStatus} helperText={emailStatus ? "email is required" : ''} id="filled-basic3" label="Email" variant="filled" required   onChange={e => {setEmail(e.target.value); setEmailStatus(!e.target.value.trim())}}/><br/>
                                <TextField error={roleStatus} helperText={roleStatus ? "role is required" : ''}  required  onChange={e => {setRole(e.target.value); setRoleStatus(!e.target.value.trim())}}
                                    id="filled-select-currency-native"
                                    select
                                    label="Role"
                                    SelectProps={{
                                        native: true,
                                    }}
                                    variant="filled"
                                    >
                                    {[{id:"staff", role:"staff"}, {id:"student", role:"student"}].map((option) => (
                                        <option key={option.id} value={option.role}>
                                        {option.role}
                                        </option>
                                    ))}
                                    </TextField>
                                    <Button id="register" className='login-btn' variant="contained" onClick={handleSubmit} >Register</Button>
                            </>
                            : 
                            <Button  id="login" className='login-btn' variant="contained" onClick={handleSubmit}>Login</Button>
                        }
                        
                        </Box>
                    </div>
                </form>
            </div>
        </div>
        </>
    );
};





export default MyLoginPage;