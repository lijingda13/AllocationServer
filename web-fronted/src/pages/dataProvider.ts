import { DataProvider, fetchUtils } from "react-admin";
import { BACKEND_URL } from "../share/env.js";
import { Url } from "../share/url.js";
import '../mock/mock.js';

const httpClient = fetchUtils.fetchJson;
const userid = localStorage.getItem("username");
const token = localStorage.getItem("token");
const headers = new Headers();
headers.append('Content-Type', 'application/json');
 
export const dataProvider1: DataProvider = {
    /** get projects list */
    getList: (resource, params) => {
        const token = (localStorage.getItem("token") || '');
        if (!headers.has("Authorization")) {
            headers.append("Authorization", `Bearer ${token}`);
        }
        let url;
        if (localStorage.getItem("role") === 'STAFF') {
            url = BACKEND_URL + Url.projects_staff_get(localStorage.getItem("userid"));
        } else {
            if (params.filter.category === '2') {
                url = BACKEND_URL + Url.projects_student_assigned_get;
            } else {
                url = BACKEND_URL + Url.projects_student_available_get(localStorage.getItem('userid'));
            }
        }
        const result =  httpClient(url, {method:'GET', headers}).then(({ headers,json }) => {
            // console.log('getlist:',params, json, localStorage.getItem("role"))
            return ({
            data: json,
            total: json?.length || 0,
        })});
        return result
    },

    /** get one project by id */
    getOne: async (resource, params) => {
            const url = BACKEND_URL + Url.projects_id_get(params.id);
            const { json } = await httpClient(url, {method:'GET', headers});
            return { data: json };
        },

    getMany: () => {
        const url = `${BACKEND_URL}/test`;
        return httpClient(url).then(({ json }) => ({ data: json }));
    },

    getManyReference: () => {
        const url = `${BACKEND_URL}/test`;
        return httpClient(url).then(({ headers, json }) => ({
            data: json,
            total: parseInt((headers.get('content-range') || "0").split('/').pop() || '0', 10),
        }));
    },

    /** assign student */
    update: (resource, params) =>{
        const resData = {...params.data};
        resData.student = resData.assignInterestStudents;
        delete resData.assignInterestStudents;
        const student = {student_id: params.meta.student_id};
        const url = `${BACKEND_URL}/projects/${params.id}/assign`;
       return httpClient(url, {
            method: 'POST',
            headers,
            body: JSON.stringify(student),
        }).then(({ json }) => {
            console.log('json:',json)
            json.id=111; // 勿删：必要！
            return {data: json }
        });
},

    updateMany: () => {
        return httpClient(`${BACKEND_URL}/test`, {
            method: 'PUT',
        }).then(({ json }) => ({ data: json }));
    },

    /** create a project */
    create: (resource, params) =>{
        const url = BACKEND_URL + Url.projects_post;
        return httpClient(url, {
            method: 'POST',
            headers,
            body: JSON.stringify(params.data),
        }).then(({json}) => {
            console.log(json)
            json.id=2444444; // 勿删: id必要！
            return {data: json};
        })
    },

    /** delete:  */
    delete: (resource, params) =>
        httpClient(`${BACKEND_URL}/test`, {
            method: 'DELETE',
        }).then(({ json }) => ({ data: json })),

    deleteMany: (resource, params) => {
        const query = {
            filter: JSON.stringify({ id: params.ids}),
        };
        return httpClient(`${BACKEND_URL}/test`, {
            method: 'DELETE',
        }).then(({ json }) => ({ data: json }));
    },

    /** get user by id */
    getUser: () => {
        const userid = localStorage.getItem("userid")
        const token = localStorage.getItem("token")||'';
        if (!headers.has('Authorization')) {
            headers.append('Authorization', `Bearer ${token}`);
        }
        const url = `${BACKEND_URL}${Url.user_id_get(userid)}`;
        const result = fetch(url, {
            method: 'GET',
            headers
        }).then((response:any) => response.json());
        
        return result
    },

    /** update user */
    saveUser: async (data: any) => {
        const userid = localStorage.getItem("userid")
        const token = localStorage.getItem("token") || '';
        if (!headers.has("Authorization")) {
            headers.append("Authorization", `Bearer ${token}`);
        }
        const url = `${BACKEND_URL}${Url.user_id_patch(userid)}`;
        const result = await fetch(url, {
            method: 'PATCH',
            headers,
            body: toJsonString(data)
        }).then((response:any) => {
            if (!response.ok) {
                return response.json().then((data: any) => {
                    return Promise.reject(data.message);
                });
            } else {
                return response.json();
            }
        });
        return result;
    },

    /** student register interest */
    registerInterest: (id:any) => {
        const user = {student_id: 315}
        const url = `${BACKEND_URL}/projects/${id}/interest`;
        const result = fetch(url, {
            method: 'POST',
            headers,
            body: toJsonString(user)
        }).then((response:any) => response.json());
        return result;
    }
};

/** utils: json string */
const toJsonString = (params: any) => {
    return JSON.stringify(params || '');
}

