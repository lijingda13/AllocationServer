import * as React from 'react';
import { useState } from 'react';
import { useLogin, useNotify } from 'react-admin';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Button from '@mui/material/Button';

import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';

import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { BACKEND_URL } from '../share/env';
import { Url } from '../share/url';

import IconButton from '@mui/material/IconButton';
import FilledInput from '@mui/material/FilledInput';
import InputLabel from '@mui/material/InputLabel';
import InputAdornment from '@mui/material/InputAdornment';
import FormControl from '@mui/material/FormControl';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import FormHelperText from '@mui/material/FormHelperText';


const MyLoginPage = () => {
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [role, setRole] = useState('STAFF');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [usernameStatus, setUsernameStatus] = useState(false);
    const [passwordStatus, setPasswordStatus] = useState(false);
    const [emailStatus, setEmailStatus] = useState(false);
    const [roleStatus, setRoleStatus] = useState(false);
    const [firstNameStatus, setFirstNameStatus] = useState(false);
    const [lastNameStatus, setLastNameStatus] = useState(false);
    const [value, setValue] = useState(0);
    const login = useLogin();
    const notify = useNotify();


    const [showPassword, setShowPassword] = useState(false);

    const handleClickShowPassword = () => setShowPassword((show) => !show);

    const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
    };

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

        if (!firstName.trim() && action === 'register') {
            setFirstNameStatus(true);
            return;
        }
        setFirstNameStatus(false);

        if (!lastName.trim() && action === 'register') {
            setLastNameStatus(true);
            return;
        }
        setLastNameStatus(false);

        if (action === 'login') {
            // will call authProvider.login({ email, password })
            login({ username, password }).catch(() =>{
                return notify("Some of your information isn't correct. Please try again.", {type:"error"})
            });
        } else {
            const params = JSON.stringify({username, password, email, role, firstName, lastName});
            const result = await fetch(BACKEND_URL + Url.users_post, {method: 'POST', headers, body: params}).then(response => {
                if (response.status == 400) {
                    return response.text().then(data => {
                      return Promise.reject(data);
                    });
                  } else {
                    return response.text();
                  }
            }).then(res => {
                notify(res, { type: 'success' });
                setValue(0); // go to login tab
                setEmail('');
                setFirstName('');
                setLastName('');
            }).catch(e => {
                notify(e, { type: 'error' });
            });
        }
    
    };

    // handle tab
    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
        setRole("STAFF");
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
                        <Box
                        component="form"
                        sx={{
                            '& .MuiTextField-root': { m: 1, width: '25ch' },
                            '& > :not(style)': { m: 1, width: '25ch' },
                        }}
                        
                        autoComplete="off"
                        >
                        <TextField error={usernameStatus} helperText={usernameStatus ? "username is required" : ''} className='customize-field' id="filled-basic1" label="Username" variant="filled" required onChange={e => {setUsername(e.target.value); setUsernameStatus(!e.target.value.trim())}}/><br/>
                        <FormControl error={passwordStatus} sx={{ m: 1, width: '25ch' }} variant="filled">
                            <InputLabel  required htmlFor="filled-adornment-password">Password</InputLabel>
                            <FilledInput
                                onChange={e => {setPassword(e.target.value); ; setPasswordStatus(!e.target.value.trim())}}
                                id="filled-adornment-password"
                                type={showPassword ? 'text' : 'password'}
                                endAdornment={
                                <InputAdornment className="password-icon" position="end">
                                    <IconButton
                                    aria-label="toggle password visibility"
                                    onClick={handleClickShowPassword}
                                    onMouseDown={handleMouseDownPassword}
                                    edge="end"
                                    >
                                    {showPassword ? <VisibilityOff /> : <Visibility />}
                                    </IconButton>
                                </InputAdornment>
                                }
                            />
                            <FormHelperText sx={{display: passwordStatus ? "inherit" : "none"}} id="component-error-text">password is required</FormHelperText>
                        </FormControl><br/>
                        {value==1? 
                            <>
                                <TextField error={emailStatus} helperText={emailStatus ? "email is required" : ''} id="filled-basic3" label="Email" variant="filled" required onChange={e => {setEmail(e.target.value); setEmailStatus(!e.target.value.trim())}}/><br/>
                                <TextField error={firstNameStatus} helperText={firstNameStatus ? "first name is required" : ''}  id="filled-basic5" label="First Name" variant="filled" required onChange={e => {setFirstName(e.target.value); setFirstNameStatus(!e.target.value.trim())}} /><br/>
                                <TextField error={lastNameStatus} helperText={lastNameStatus ? "last name is required" : ''}  id="filled-basic6" label="Last Name" variant="filled" required onChange={e => {setLastName(e.target.value); setLastNameStatus(!e.target.value.trim())}} /><br/>

                                <TextField error={roleStatus} helperText={roleStatus ? "role is required" : ''}  required  onChange={e => {setRole(e.target.value); setRoleStatus(!e.target.value.trim())}}
                                    id="filled-select-currency-native"
                                    select
                                    label="Role"
                                    SelectProps={{
                                        native: true,
                                    }}
                                    variant="filled"
                                    >
                                    {[{id:"STAFF", role:"STAFF"}, {id:"STUDENT", role:"STUDENT"}].map((option) => (
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