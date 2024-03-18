import fetchMock from 'fetch-mock';

const data = [
    {
        "id": 111,
        "title": "23",
        "description": "dddddchangj",
        "staff": 211,
        "student": null,
        "status": true,
        "interestStudents": [
            {"id": 311,name: "zz",},
            {"id": 312,name: "44",},
            {"id": 313,name: "tt",},
            {"id": 314,name: "rr",},
            {"id": 315,name: "rr",},
            {"id": 316,name: "rr",},
            {"id": 317,name: "rr",},
            {"id": 318,name: "rr",},
            {"id": 319,name: "rr",},
        ]
    },
    {
        "id": 112,
        "title": "23",
        "description": "ddddd",
        "staff": 211,
        "student": 311,
        "status": false,
        "interestStudents": [
            {"id": 311,name: "zz",},
            {"id": 312,name: "44",},
            {"id": 313,name: "tt",},
            {"id": 314,name: "rr",},
        ]
    },
    {
        "id": 113,
        "title": "23",
        "description": "ddddd",
        "staff": 211,
        "student": 311,
        "status": true,
        "interestStudents": [
            {"id": 311,name: "zz",},
            {"id": 312,name: "44",},
            {"id": 313,name: "tt",},
            {"id": 314,name: "rr",},
        ]
    },
    {
        "id": 114,
        "title": "23",
        "description": "ddddd",
        "staff": 211,
        "student": 311,
        "status": true,
        "interestStudents": [
            {"id": 311,name: "zz",},
            {"id": 312,name: "44",},
            {"id": 313,name: "tt",},
            {"id": 314,name: "rr",},
        ]
    },
    {
        "id": 115,
        "title": "23",
        "description": "ddddd",
        "staff": 211,
        "student": 311,
        "status": true,
        "interestStudents": [
            {"id": 311,name: "zz",},
            {"id": 312,name: "44",},
            {"id": 313,name: "tt",},
            {"id": 314,name: "rr",},
        ]
    },
    {
        "id": 116,
        "title": "23",
        "description": "ddddd",
        "staff": 211,
        "student": 311,
        "status": true,
        "interestStudents": [
            {"id": 311,name: "zz",},
            {"id": 312,name: "44",},
            {"id": 313,name: "tt",},
            {"id": 314,name: "rr",},
        ]
    },
    {
        "id": 117,
        "title": "23",
        "description": "ddddd",
        "staff": 211,
        "student": 311,
        "status": true,
        "interestStudents": [
            {"id": 311,name: "zz",},
            {"id": 312,name: "44",},
            {"id": 313,name: "tt",},
            {"id": 314,name: "rr",},
        ]
    },
    {
        "id": 118,
        "title": "23",
        "description": "ddddd",
        "staff": 211,
        "student": 311,
        "status": true,
        "interestStudents": [
            {"id": 311,name: "zz",},
            {"id": 312,name: "44",},
            {"id": 313,name: "tt",},
            {"id": 314,name: "rr",},
        ]
    },
    {
        "id": 119,
        "title": "23",
        "description": "ddddd",
        "staff": 211,
        "student": 311,
        "status": true,
        "interestStudents": [
            {"id": 311,name: "zz",},
            {"id": 312,name: "44",},
            {"id": 313,name: "tt",},
            {"id": 314,name: "rr",},
        ]
    },
    {
        "id": 120,
        "title": "23",
        "description": "ddddd",
        "staff": 211,
        "student": 311,
        "status": true,
        "interestStudents": [
            {"id": 311,name: "zz",},
            {"id": 312,name: "44",},
            {"id": 313,name: "tt",},
            {"id": 314,name: "rr",},
        ]
    },{
        "id": 121,
        "title": "23",
        "description": "ddddd",
        "staff": 211,
        "student": 311,
        "status": true,
        "interestStudents": [
            {"id": 311,name: "zz",},
            {"id": 312,name: "44",},
            {"id": 313,name: "tt",},
            {"id": 314,name: "rr",},
        ]
    },
    {
        "id": 122,
        "title": "23",
        "description": "ddddd",
        "staff": 211,
        "student": 311,
        "status": true,
        "interestStudents": [
            {"id": 311,name: "zz",},
            {"id": 312,name: "44",},
            {"id": 313,name: "tt",},
            {"id": 314,name: "rr",},
        ]
    },
    {
        "id": 123,
        "title": "23",
        "description": "ddddd",
        "staff": 211,
        "student": 311,
        "status": true,
        "interestStudents": [
            {"id": 311,name: "zz",},
            {"id": 312,name: "44",},
            {"id": 313,name: "tt",},
            {"id": 314,name: "rr",},
        ]
    },
    {
        "id": 124,
        "title": "23",
        "description": "ddddd",
        "staff": 211,
        "student": 311,
        "status": true,
        "interestStudents": [
            {"id": 311,name: "zz",},
            {"id": 312,name: "44",},
            {"id": 313,name: "tt",},
            {"id": 314,name: "rr",},
        ]
    },
    {
        "id": 125,
        "title": "23",
        "description": "ddddd",
        "staff": 211,
        "student": null,
        "status": false,
        "interestStudents": [
            {"id": 311,name: "zz",},
            {"id": 312,name: "44",},
            {"id": 313,name: "tt",},
            {"id": 314,name: "rr",},
        ]
    },
    {
        "id": 126,
        "title": "23",
        "description": "ddddd",
        "staff": 211,
        "student": null,
        "status": false,
        "interestStudents": [
            {"id": 311,name: "zz",},
            {"id": 312,name: "44",},
            {"id": 313,name: "tt",},
            {"id": 314,name: "rr",},
        ]
    }
]
const baseUrl = "http://localhost:5173";
fetchMock.get(`${baseUrl}/mock/get/projects`, data);

const getProjectById = (id) => {
    return data.find(v => v.id == id);
}
fetchMock.get(`${baseUrl}/mock/get/project/111`, getProjectById(111));
fetchMock.get(`${baseUrl}/mock/get/project/112`, getProjectById(112));

fetchMock.post(`${baseUrl}/mock/login`, {status:200, data: "successful"});
fetchMock.mock(`${baseUrl}/mock/register`, {status:201, data:"successful"});