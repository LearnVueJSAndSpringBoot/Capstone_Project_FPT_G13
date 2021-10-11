import http from "./http-common";

class demoaxios {
    getAll(form) {
        return http.post(`/spadmin/employee`, form);
    }

    get(id) {
        return http.get(`/tutorials/${id}`);
    }

    create(data) {
        return http.post("/tutorials", data);
    }

    update(id, data) {
        return http.put(`/tutorials/${id}`, data);
    }

    delete(id) {
        return http.delete(`/tutorials/${id}`);
        //abc/1
        //path variable
    }

    deleteAll() {
        return http.delete(`/tutorials`);
    }

    findByTitle(title) {
        return http.get(`/tutorials?title=${title}`);
        //abc?id=1
        //query param
    }

    upload(file) {
        let formData = new FormData();
        formData.append("file", file);
        return http.post("files/upload", formData, {
            headers: {
                "Content-Type": "multipart/form-data"
            },
        });
    }

    getFiles() {
        return http.get("/files");
    }
}

export default new demoaxios();