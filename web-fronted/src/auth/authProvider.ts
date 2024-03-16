import { AuthProvider } from "react-admin";
import {BACKEND_URL} from "../share/env";

export const authProvider: AuthProvider = {
    // called when the user attempts to log in
    login: ({username, password}) => {

        return fetch(`${BACKEND_URL}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username,
                password
            }),
        })
        .then(response => {
            console.log(response);
            if (response.status === 200) {
                // successed
                localStorage.setItem("username", username);
                return Promise.resolve();
            } else {
                // failed
                throw new Error("Some of your information isn't correct. Please try again.");
            }
        });

        // localStorage.setItem("username", username);
        // localStorage.setItem("role", "staff");
        // setTimeout(() => {
        //     window.location.reload()
        // }, 1000);
        
        // // accept all username/password combinations
        // return Promise.resolve();
    },
    // called when the user clicks on the logout button
    logout: () => {
        localStorage.removeItem("username");
        localStorage.removeItem("role");
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
    // getIdentity:() => Promise.resolve(),
};