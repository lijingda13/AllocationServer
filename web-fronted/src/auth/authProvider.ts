import { AuthProvider, useNotify } from "react-admin";
import {BACKEND_URL} from "../share/env";
import { DataProvider, fetchUtils } from "react-admin";
import { Url } from "../share/url";

const httpClient = fetchUtils.fetchJson;
const headers = new Headers();
headers.append('Content-Type', 'application/json');
export const authProvider: AuthProvider = {
    
    // called when the user attempts to log in
    login: async ({username, password}) => {

        return await httpClient(`${BACKEND_URL}${Url.login_post}`, {
            method: 'POST',
            headers,
            body: JSON.stringify({
                username,
                password
            }),
        })
        .then(response => {
            if (response.status == 200) {
                const userdata = response.json;
                localStorage.setItem("username", userdata.user.username);
                localStorage.setItem("userid", userdata.user.id);
                localStorage.setItem("role", userdata.user.role);
                localStorage.setItem("token", userdata.token);
                return Promise.resolve();
            }
        });
    },
    // called when the user clicks on the logout button
    logout: () => {
        localStorage.removeItem("username");
        localStorage.removeItem("userid");
        localStorage.removeItem("role");
        localStorage.removeItem("token");
        return Promise.resolve();
    },
    // called when the API returns an error
    checkError: ({ status }: { status: number }) => {
        if (status === 401 || status === 403) {
            localStorage.removeItem("username");
            localStorage.removeItem("role");
            return Promise.reject();
        }
        return Promise.resolve();
    },
    // called when the user navigates to a new location, to check for authentication
    checkAuth: () => {
        return localStorage.getItem("username")
            ? Promise.resolve()
            : Promise.reject();
    },
    // called when the user navigates to a new location, to check for permissions / roles
    getPermissions: () => Promise.resolve(),
};