import { Admin,CustomRoutes,Resource } from "react-admin";
import { dataProvider1 } from "./dataProvider";
import PostIcon from '@mui/icons-material/LibraryBooks';
import { UserList } from "./users";
import { PostList, PostEdit, PostCreate } from "./projects";
import { Dashboard } from "./Dashboard";
import { authProvider } from "../auth/authProvider";
import { MyLayout } from "./layout";
import MyLoginPage from "../auth/MyLoginPage";
import { useEffect, useState } from "react";
import { Route } from "react-router-dom";

export const App = () => {
  const [role, setRole] = useState('');
  const [token, setToken] = useState('');
  useEffect(() => {
    const storedRole = localStorage.getItem('role');
    const storedToken = localStorage.getItem('token');

    if (storedRole !== null) {
      setRole(storedRole);
    }

    if (storedToken !== null) {
      setToken(storedToken);
    }
  }, []);
    
  return (
      <Admin loginPage={MyLoginPage} layout={MyLayout} authProvider={authProvider} dataProvider={dataProvider1} dashboard={Dashboard}>
        <Resource
              name="projects"
              list={PostList}
              edit={PostEdit}
              icon={PostIcon}
              create={role === "staff"?PostCreate : undefined}
          /> 
          {/* <Resource
              name="Users"
              list={UserList}
              icon={UserIcon}
          /> */}
          <CustomRoutes>
            <Route path="/user" element={<UserList />} />
        </CustomRoutes>
      </Admin>
)};


