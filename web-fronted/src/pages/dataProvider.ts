import { DataProvider, GetOneParams, GetOneResult, fetchUtils } from "react-admin";
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
                url = BACKEND_URL + Url.projects_student_assigned_get(localStorage.getItem('userid'));
            } else {
                url = BACKEND_URL + Url.projects_student_available_get(localStorage.getItem('userid'));
            }
        }
        const result =  httpClient(url, {method:'GET', headers}).then(({ headers,json }) => {
            const resData = params.filter.category === '2' ? (json.assignedProject ? [json.assignedProject] : []) : json;
            // console.log('getlist:',params, json, localStorage.getItem("role"))
            return ({
            data: resData,
            total: resData?.length || 0,
        })});
        return result
    },

    /** get one project by id */
    getOne: async (resource: string, params: GetOneParams<any>): Promise<GetOneResult<any>> => {
        console.log(resource, params);
        // const json: any = {id:1, name: "123" };
        return { data: params };
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
        const token = localStorage.getItem("token")||'';
        if (!headers.has('Authorization')) {
            headers.append('Authorization', `Bearer ${token}`);
        }
        const student = {...params.meta.student};
        delete student.password;
        const url = BACKEND_URL + Url.projects_id_assign_post(params.meta.projectId);
       return httpClient(url, {
            method: 'POST',
            headers,
            body: JSON.stringify(student),
        }).then(({ json }) => {
            // console.log('json:',json)
            json.id=params.meta.projectId; // 勿删：必要！
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
        const userid = localStorage.getItem("userid")
        const token = localStorage.getItem("token")||'';
        if (!headers.has('Authorization')) {
            headers.append('Authorization', `Bearer ${token}`);
        }
        const url = BACKEND_URL + Url.projects_create_uesrid_post(userid);
        return httpClient(url, {
            method: 'POST',
            headers,
            body: JSON.stringify(params.data),
        }).then(({json}) => {
            console.log(json)
            // json.id=2444444; // 勿删: id必要！
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
        const token = localStorage.getItem("token") || '';
        if (!headers.has("Authorization")) {
            headers.append("Authorization", `Bearer ${token}`);
        }
        // const user = {student_id: 315}
        const url = BACKEND_URL + Url.projects_id_registerinterest_post(id);
        const result = fetch(url, {
            method: 'POST',
            headers
        }).then((response:any) => {
            if (!response.ok) {
                return response.text().then((data: any) => {
                    return Promise.reject(data)
                });
            } else {
                response.text();
            }
        });
        return result;
    },

    /** student cancel interest */
    cancelInterest: (id:any) => {
        const token = localStorage.getItem("token") || '';
        if (!headers.has("Authorization")) {
            headers.append("Authorization", `Bearer ${token}`);
        }
        // const user = {student_id: 315}
        const url = BACKEND_URL + Url.projects_id_unregisterinterest_post(id);
        const result = fetch(url, {
            method: 'POST',
            headers
        }).then((response:any) => {
            if (!response.ok) {
                return response.text().then((data: any) => {
                    return Promise.reject(data)
                });
            } else {
                response.text();
            }
        });
        return result;
    },

    /** staff delete project by id */
    deleteProject: (projectId: any) => {
        const token = localStorage.getItem("token") || '';
        if (!headers.has("Authorization")) {
            headers.append("Authorization", `Bearer ${token}`);
        }
        const url = BACKEND_URL + Url.project_id_delete(projectId);
        const result = fetch(url, {
            method: 'DELETE',
            headers
        }).then((response:any) => {
            if (!response.ok) {
                return response.text().then((data: any) => {
                    return Promise.reject(data)
                });
            } else {
                response.text();
            }
        });
        return result;
    }
};

/** utils: json string */
const toJsonString = (params: any) => {
    return JSON.stringify(params || '');
}

