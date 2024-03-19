import { AppBar, Layout, Logout, UserMenu, useRedirect } from 'react-admin';
import * as React from 'react';
import {useUserMenu } from 'react-admin';
import { MenuItem, ListItemIcon, ListItemText } from '@mui/material';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import ErrorIcon from '@mui/icons-material/Report';
import { Title } from 'react-admin';

/** error page */
const MyError = () => {
    return (
        <div>
            <Title title="Error" />
            <h1><ErrorIcon /> Something Went Wrong </h1>
            <div>A client error occurred and your request couldn't be completed.</div>
        </div>
    );
};

/** accout menu */
const SettingsMenuItem = React.forwardRef((props, ref) => {
    // We are not using MenuItemLink so we retrieve the onClose function from the UserContext
    const { onClose } = useUserMenu();
    const redirect = useRedirect();
    const toUser = () => {
        redirect('/user');
    }
    const handleClick = () => {
        onClose();
        toUser();
    }
    return (
        <MenuItem
            onClick={handleClick}
        >
            <ListItemIcon>
                <AccountCircleIcon fontSize="small" />
            </ListItemIcon>
            <ListItemText>My Account</ListItemText>
        </MenuItem>
    );
});

const MyAppBar = () => (
    <AppBar userMenu={
        <UserMenu>
            <SettingsMenuItem />
            <Logout />
        </UserMenu>
    }/>
        
);

export const MyLayout = (props: any) => <Layout {...props}  error={MyError} appBar={MyAppBar}/>;

