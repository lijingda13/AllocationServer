export const Url = {
    login_post : "/login", // login
    logout_post : "/logout", // logout

    users_post : "/users", // register a user
    user_id_get : (id:any) => `/users/${id}`, // get user by id
    user_id_patch: (id:any) => `/users/${id}`, // update user by id

    projects_staff_get: "/projects/staff", // get staff's projects.
    projects_student_available_get: "/projects/student/available", // get available projects.
    projects_student_assigned_get: "/projects/student/assigned", // get student's assigned projects .
    projects_id_get: (id: any) => `/projects/${id}`, // get project by project id.
    projects_post: "/projects", // create a project
    projects_id_assign_post: (id: any) => `/projects/${id}/assign`, // asign a project to a student


}