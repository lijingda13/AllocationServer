import { Layout } from 'react-admin';

import ErrorIcon from '@mui/icons-material/Report';
import { Title } from 'react-admin';

export const MyError = () => {
    return (
        <div>
            <Title title="Error" />
            <h1><ErrorIcon /> Something Went Wrong </h1>
            <div>A client error occurred and your request couldn't be completed.</div>
        </div>
    );
};

export const MyLayout = (props: any) => <Layout {...props}  error={MyError}/>;

