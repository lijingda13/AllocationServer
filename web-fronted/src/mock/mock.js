// import fetchMock from 'fetch-mock';

// const data = [
//     {
//         "id": 111,
//         "title": "23",
//         "description": "dddddchangjdddddchangjdddddchangjdddddchangjdddddchangjdddddchangjdddddchangjdddddchangjdddddchangjdddddchangjdddddchangjdddddchangjdddddchangjdddddchangj",
//         "staff": 211,
//         "student": null,
//         "status": true,
//         "registerStatus": true,
//         "interestStudents": [
//             {"id": 311,name: "zz",},
//             {"id": 312,name: "44",},
//             {"id": 313,name: "tt",},
//             {"id": 314,name: "rr",},
//             {"id": 315,name: "rr",},
//             {"id": 316,name: "rr",},
//             {"id": 317,name: "rr",},
//             {"id": 318,name: "rr",},
//             {"id": 319,name: "rr",},
//         ]
//     },
//     {
//         "id": 112,
//         "title": "23",
//         "description": "ddddd",
//         "staff": 211,
//         "student":null,
//         "status": false,
//         "registerStatus": true,
//         "interestStudents": []
//     },
//     {
//         "id": 113,
//         "title": "23",
//         "description": "ddddd",
//         "staff": 211,
//         "student": 311,
//         "status": true,
//         "registerStatus": false,
//         "interestStudents": [
//             {"id": 311,name: "zz",},
//             {"id": 312,name: "44",},
//             {"id": 313,name: "tt",},
//             {"id": 314,name: "rr",},
//         ]
//     },
//     {
//         "id": 114,
//         "title": "23",
//         "description": "ddddd",
//         "staff": 211,
//         "student": 311,
//         "status": true,
//         "registerStatus": true,

//         "interestStudents": [
//             {"id": 311,name: "zz",},
//             {"id": 312,name: "44",},
//             {"id": 313,name: "tt",},
//             {"id": 314,name: "rr",},
//         ]
//     },
//     {
//         "id": 115,
//         "title": "23",
//         "description": "ddddd",
//         "staff": 211,
//         "student": 311,
//         "status": true,
//         "registerStatus": true,

//         "interestStudents": [
//             {"id": 311,name: "zz",},
//             {"id": 312,name: "44",},
//             {"id": 313,name: "tt",},
//             {"id": 314,name: "rr",},
//         ]
//     },
//     {
//         "id": 116,
//         "title": "23",
//         "description": "ddddd",
//         "staff": 211,
//         "student": 311,
//         "status": true,
//         "registerStatus": true,

//         "interestStudents": [
//             {"id": 311,name: "zz",},
//             {"id": 312,name: "44",},
//             {"id": 313,name: "tt",},
//             {"id": 314,name: "rr",},
//         ]
//     },
//     {
//         "id": 117,
//         "title": "23",
//         "description": "ddddd",
//         "staff": 211,
//         "student": 311,
//         "status": true,
//         "registerStatus": true,

//         "interestStudents": [
//             {"id": 311,name: "zz",},
//             {"id": 312,name: "44",},
//             {"id": 313,name: "tt",},
//             {"id": 314,name: "rr",},
//         ]
//     },
//     {
//         "id": 118,
//         "title": "23",
//         "description": "ddddd",
//         "staff": 211,
//         "student": 311,
//         "status": true,
//         "registerStatus": true,

//         "interestStudents": [
//             {"id": 311,name: "zz",},
//             {"id": 312,name: "44",},
//             {"id": 313,name: "tt",},
//             {"id": 314,name: "rr",},
//         ]
//     },
//     {
//         "id": 119,
//         "title": "23",
//         "description": "ddddd",
//         "staff": 211,
//         "student": 311,
//         "status": true,
//         "registerStatus": true,

//         "interestStudents": [
//             {"id": 311,name: "zz",},
//             {"id": 312,name: "44",},
//             {"id": 313,name: "tt",},
//             {"id": 314,name: "rr",},
//         ]
//     },
//     {
//         "id": 120,
//         "title": "23",
//         "description": "ddddd",
//         "staff": 211,
//         "student": 311,
//         "status": true,
//         "registerStatus": true,

//         "interestStudents": [
//             {"id": 311,name: "zz",},
//             {"id": 312,name: "44",},
//             {"id": 313,name: "tt",},
//             {"id": 314,name: "rr",},
//         ]
//     },{
//         "id": 121,
//         "title": "23",
//         "description": "ddddd",
//         "staff": 211,
//         "student": 311,
//         "status": true,
//         "registerStatus": true,

//         "interestStudents": [
//             {"id": 311,name: "zz",},
//             {"id": 312,name: "44",},
//             {"id": 313,name: "tt",},
//             {"id": 314,name: "rr",},
//         ]
//     },
//     {
//         "id": 122,
//         "title": "23",
//         "description": "ddddd",
//         "staff": 211,
//         "student": 311,
//         "status": true,
//         "registerStatus": true,

//         "interestStudents": [
//             {"id": 311,name: "zz",},
//             {"id": 312,name: "44",},
//             {"id": 313,name: "tt",},
//             {"id": 314,name: "rr",},
//         ]
//     },
//     {
//         "id": 123,
//         "title": "23",
//         "description": "ddddd",
//         "staff": 211,
//         "student": 311,
//         "status": true,
//         "registerStatus": true,

//         "interestStudents": [
//             {"id": 311,name: "zz",},
//             {"id": 312,name: "44",},
//             {"id": 313,name: "tt",},
//             {"id": 314,name: "rr",},
//         ]
//     },
//     {
//         "id": 124,
//         "title": "23",
//         "description": "ddddd",
//         "staff": 211,
//         "student": 311,
//         "status": true,
//         "registerStatus": true,

//         "interestStudents": [
//             {"id": 311,name: "zz",},
//             {"id": 312,name: "44",},
//             {"id": 313,name: "tt",},
//             {"id": 314,name: "rr",},
//         ]
//     },
//     {
//         "id": 125,
//         "title": "23",
//         "description": "ddddd",
//         "staff": 211,
//         "student": null,
//         "status": false,
//         "registerStatus": true,

//         "interestStudents": [
//             {"id": 311,name: "zz",},
//             {"id": 312,name: "44",},
//             {"id": 313,name: "tt",},
//             {"id": 314,name: "rr",},
//         ]
//     },
//     {
//         "id": 126,
//         "title": "23",
//         "description": "ddddd",
//         "staff": 211,
//         "student": null,
//         "status": false,
//         "registerStatus": true,

//         "interestStudents": [
//             {"id": 311,name: "zz",},
//             {"id": 312,name: "44",},
//             {"id": 313,name: "tt",},
//             {"id": 314,name: "rr",},
//         ]
//     }
// ]
// const avai_data = data.filter(v => v.status);
// const users = [
//     {id: 211, username:"john", lastname:"Will", firstname:"Jhon", password: "123456", role: "staff", email:"john@email.com"},
//     {id: 311, username:"tom", lastname:"Will", firstname:"tom", password: "123456", role: "student", email:"tom@email.com"},
// ]
// const baseUrl = "http://localhost:5173";
// fetchMock.get(`${baseUrl}/projects/staff`, data); 
// fetchMock.get(`${baseUrl}/projects/student/available`, avai_data); 
// fetchMock.get(`${baseUrl}/projects/student/assigned`, [data[0]]); 

// const getProjectById = (id) => {
//     return data.find(v => v.id == id);
// }
// fetchMock.get(`${baseUrl}/projects/111`, getProjectById(111));
// fetchMock.get(`${baseUrl}/projects/112`, getProjectById(112));


// // register
// fetchMock.mock(`${baseUrl}/users`, (url, opts) => {
//     const body = JSON.parse(opts.body);
//     if (body.username !== 'john') {
//         return {status:201, body: {message:" register successfully!"}}
//     } else {
//         return {status: 400, body: {message: " exting username"}}
//     }
    
// });
// //login
// fetchMock.mock(`${baseUrl}/login`,(url, opts) => {
//     const body = JSON.parse(opts.body);
//     if (body.username === 'john' & body.password==='123456') {
//       return { status: 200, body: {id: 211, role: 'staff', token: 'wewrwewewewe'} };
//     }if (body.username === 'tom' & body.password==='123456') {
//         return { status: 200, body: {id: 311, role: 'student', token: 'tttrtrtrtrtrt'} };
//     } else {
//       return { status: 400, body: { message: 'Invalid username or password' } };
//     }
// });



// //logout
// fetchMock.mock(`${baseUrl}/logout`,(url, opts) => {
//     const body = JSON.parse(opts.body);
//     if (true) {
//       return { status: 200, body: {message: "logout"} };
//     } else {
//       return { status: 400, body: { message: 'logout failed' } };
//     }
// });


// // get user information
// fetchMock.get(`${baseUrl}/users/211`,() => {
//     const issuccess = true;
//     if (issuccess) {
//         return { status: 200, body: {data:users[0]}};
//     } else {
//         return {status: 400, body: null}
//     }
// })
// // get user information
// fetchMock.get(`${baseUrl}/users/311`,() => {
//     const issuccess = true;
//     if (issuccess) {
//         return { status: 200, body: {data:users[1]}};
//     } else {
//         return {status: 400, body: null}
//     }
// })

// // update user
// fetchMock.patch(`${baseUrl}/users/211`,(url,opts) => {
//     const body = JSON.parse(opts.body);
//     const issuccess = true;
//     if (issuccess) {
//         return { status: 200, body: {data:opts.body}};
//     } else {
//         return {status: 400, body: null}
//     }
// })
// // update user
// fetchMock.patch(`${baseUrl}/users/311`,(url,opts) => {
//     const body = JSON.parse(opts.body);
//     const issuccess = true;
//     if (issuccess) {
//         return { status: 200, body: {data:opts.body}};
//     } else {
//         return {status: 400, body: null}
//     }
// })

// // create project
// fetchMock.post(`${baseUrl}/projects`,(url,opts) => {
//     const body = JSON.parse(opts.body);
//     if (body.title !== "23") {
//         return { status: 200, body: {data:opts.body}};
//     } else {
//         return {status: 400, body: {message: "existing project"}}
//     }
// })

// fetchMock.post(`${baseUrl}/projects/111/interest`, () => {
//     return {body:{msg: "register interest successfully"}};
// })
// fetchMock.post(`${baseUrl}/projects/113/interest`, () => {
//     return {body:{msg: "register interest successfully"}};
// })

// fetchMock.post(`${baseUrl}/projects/111/assign`, () => {
//     return {body:{msg: "assign student successfully"}};
// })