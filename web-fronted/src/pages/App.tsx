import {
  Admin,
  Resource,
} from "react-admin";
import { dataProvider } from "./dataProvider";
import { dataProvider1 } from "./dataProvider";
import PostIcon from '@mui/icons-material/LibraryBooks';
import UserIcon from "@mui/icons-material/Group";
import {UserList} from "./users";
import { PostList, PostEdit, PostCreate } from "./projects";
import { Dashboard } from "./Dashboard";
import {authProvider} from "../auth/authProvider";
import { MyLayout } from "./errorPage";
import MyLoginPage from "../auth/MyLoginPage";

export const App = () => {
    const role = localStorage.getItem("role")
    console.log("role:", role)
    
  return (
      <Admin loginPage={MyLoginPage} layout={MyLayout} authProvider={authProvider} dataProvider={dataProvider1} dashboard={Dashboard}>
        
        <Resource
              name="posts"
              list={PostList}
              edit={PostEdit}
              icon={PostIcon}
              create={role === "staff"?PostCreate : undefined}
          /> 
          <Resource
              name="user"
              list={UserList}
              icon={UserIcon}
          />
      </Admin>
)};


