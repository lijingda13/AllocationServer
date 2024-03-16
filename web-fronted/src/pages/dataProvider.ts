import jsonServerProvider from "ra-data-json-server";

export const dataProvider = jsonServerProvider(
  import.meta.env.VITE_JSON_SERVER_URL
);

import { DataProvider, fetchUtils } from "react-admin";
import { stringify } from "query-string";

const apiUrl = 'http://localhost:8080';
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
        const url = `${apiUrl}/books`;
        

        const result =  httpClient(url).then(({ headers, json }) => ({
            data: json,
            // total: parseInt((headers.get('content-range') || "0").split('/').pop() || '0', 10),
            total: json?.length || 0,
        }));
        console.log(result)
        return result
    },

    getOne: (resource, params) =>
        httpClient(`${apiUrl}/${resource}/${params.id}`).then(({ json }) => ({
            data: json,
        })),

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

const dataJson = [
    {
        "id": "0fc4b9bf-ccf3-4b21-b325-03659a7fb53e",
        "title": "Pride and Prejudice",
        "description": "chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu",
        "author": "Jane Austen",
        "status": false,
        "students": [
            {"id": 12,"name": "Tom"},
            {"id": 13,"name": "Joe"},
        ]
    },
    {
        "id": "556988f6-1e3e-4127-8b8f-0829b011a647",
        "title": "Frankenstein",
        "description": "chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu",
        "author": "Mary Shelley",
        "status": false
    },
    {
        "id": "dabce441-4227-41f5-97e8-8feea94252af",
        "title": "Moby Dick",
        "description": "chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu,chaongjichangdeyigexiangmumiaoshu",
        "author": "Herman Melville",
        "status": false
    },
    {
        "id": "afa5bed1-4184-4ffc-a63c-8800a84b335c",
        "title": "To Kill a Mockingbird",
        "author": "Harper Lee",
        "status": false
    },
    {
        "id": "bed71351-51b2-4e12-b56d-c9a113b477c3",
        "title": "Les Misérables",
        "author": "Victor Hugo",
        "status": false
    },
    {
        "id": "14b390aa-7004-4d15-abae-93396a612bb7",
        "title": "The Great Gatsby",
        "author": "F. Scott Fitzgerald",
        "status": false
    },
    {
        "id": "afa9a09c-3907-44fe-9bed-8a318bc36a04",
        "title": "Sherlock Holmes",
        "author": "Arthur Conan Doyle",
        "status": false
    },
    {
        "id": "fd55141f-430e-474a-9518-136bf8f9fc66",
        "title": "The Hitch-Hiker's Guide to the Galaxy",
        "author": "Douglas Adams",
        "status": false
    },
    {
        "id": "2fa3062b-6328-4749-8ba1-b884a32e9a83",
        "title": "23",
        "author": "333",
        "status": false
    },
    {
        "id": "4917142e-2620-4264-8acc-24e5e8dad44b",
        "title": "33",
        "author": "333",
        "status": false
    },
    {
        "id": "980db23a-5003-4ada-b72d-5f327c836136",
        "title": "33",
        "author": "333",
        "status": false
    },
    {
        "id": "e1ec55c8-13fd-44da-945e-6cd8b31a9b82",
        "title": "44",
        "author": "55",
        "status": false
    },
    {
        "id": "ce3aafb5-5bf1-4924-888f-fdeab0915db7",
        "title": "66",
        "author": "77",
        "status": false
    },
    {
        "id": "98d88e78-6ecd-48d8-b7c6-9c67b48c93f8",
        "title": "88",
        "author": "99",
        "status": false
    },
    {
        "id": "7cc5d1c3-c638-4164-969c-75dd434ba5e6",
        "title": "33",
        "author": "22",
        "status": false
    },
    {
        "id": "46d6a4ad-bf2f-4b62-8944-2dd874dbef1c",
        "title": "3dsd",
        "author": "dwd",
        "status": false
    },
    {
        "id": "92cb86f9-b4fe-4df2-90ca-2df0c83ff34c",
        "title": "dwdw",
        "author": "dwdw",
        "status": false
    }
]