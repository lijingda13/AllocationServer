import jsonServerProvider from "ra-data-json-server";
import '../mock/mock.js'
export const dataProvider = jsonServerProvider(
  import.meta.env.VITE_JSON_SERVER_URL
);

import { DataProvider, fetchUtils } from "react-admin";
import { stringify } from "query-string";
import { promises } from "dns";

const apiUrl = 'http://localhost:5173';
const httpClient = fetchUtils.fetchJson;

export const dataProvider1: DataProvider = {
    getList: (resource, params) => {
        console.log(resource, params)
        // const =  
        const { page, perPage } = params.pagination;
        const { field, order } = params.sort;
        const query = {
            filter: JSON.stringify(params.filter),
        };
        // const url = `${apiUrl}/${resource}?${stringify(query)}`;
        const url = `${apiUrl}/mock/get/projects`;
        

        const result =  httpClient(url).then(({ headers, json }) => {
            console.log(json);
            return ({
            data: json,
            total: json?.length || 0,
        })});
        console.log("result:",result)
        return result
    },

    getOne: (resource, params) =>
        {
            console.log(resource, params)// "posts", "{id: '112', meta: undefined}"
           return httpClient(`${apiUrl}/mock/get/project/${params.id}`).then(({ json }) => ({
                data: json,
            }))
        // return promises.resolve(resource, '1');
        }
        ,

    getMany: (resource, params) => {
        const query = {
            filter: JSON.stringify({ id: params.ids }),
        };
        const url = `${apiUrl}/${resource}?${stringify(query)}`;
        return httpClient(url).then(({ json }) => ({ data: json }));
    },

    getManyReference: (resource, params) => {
        const { page, perPage } = params.pagination;
        const { field, order } = params.sort;
        const query = {
            sort: JSON.stringify([field, order]),
            range: JSON.stringify([(page - 1) * perPage, page * perPage - 1]),
            filter: JSON.stringify({
                ...params.filter,
                [params.target]: params.id,
            }),
        };
        const url = `${apiUrl}/${resource}?${stringify(query)}`;

        return httpClient(url).then(({ headers, json }) => ({
            data: json,
            total: parseInt((headers.get('content-range') || "0").split('/').pop() || '0', 10),
        }));
    },

    update: (resource, params) =>
        httpClient(`${apiUrl}/${resource}/${params.id}`, {
            method: 'PUT',
            body: JSON.stringify(params.data),
        }).then(({ json }) => ({ data: json })),

    updateMany: (resource, params) => {
        const query = {
            filter: JSON.stringify({ id: params.ids}),
        };
        return httpClient(`${apiUrl}/${resource}?${stringify(query)}`, {
            method: 'PUT',
            body: JSON.stringify(params.data),
        }).then(({ json }) => ({ data: json }));
    },

    create: (resource, params) =>
        httpClient(`${apiUrl}/${resource}`, {
            method: 'POST',
            body: JSON.stringify(params.data),
        }).then(({ json }) => ({
            data: { ...params.data, id: json.id } as any,
        })),

    delete: (resource, params) =>
        httpClient(`${apiUrl}/${resource}/${params.id}`, {
            method: 'DELETE',
        }).then(({ json }) => ({ data: json })),

    deleteMany: (resource, params) => {
        const query = {
            filter: JSON.stringify({ id: params.ids}),
        };
        return httpClient(`${apiUrl}/${resource}?${stringify(query)}`, {
            method: 'DELETE',
        }).then(({ json }) => ({ data: json }));
    }
};

