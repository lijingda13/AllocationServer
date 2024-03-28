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
  useEffect(() => {
    const storedRole = localStorage.getItem('role');
    if (storedRole !== null) {
      setRole(storedRole);
    }
  }, []);
    
  return (
      <Admin loginPage={MyLoginPage} layout={MyLayout} authProvider={authProvider} dataProvider={dataProvider1} dashboard={Dashboard}>
        <Resource
              name="projects"
              list={PostList}
              icon={PostIcon}
              create={PostCreate}
              hasCreate = {role === "STAFF"}
          /> 
          <CustomRoutes>
            <Route path="/projects/edit" element={<PostEdit/>} />
            <Route path="/user" element={<UserList />} />
        </CustomRoutes>
      </Admin>
)};


