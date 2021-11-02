import http from "./http-common";

class acceptUngLuongService {
    getAllAccount(user) {
        return http.post(`/spadmin/account/allaccount`, user);
    }
    searchAccount(name) {
        return http.post("/spadmin/account/searchaccount", name);
    }
    updateAccount(data) {
        return http.put(`/spadmin/account/update`, data);
    }
    getAccountByID(id) {
        return http.post(`/spadmin/account/getAcc?id=${id}`);
    }
    generateUsername(id) {
        return http.get(`/spadmin/account/generateUsername?id=${id}`);
    }
    deleteAccount(id) {
        return http.delete(`/spadmin/account/delete?id=${id}`);
    }
    addAccount(form) {
        return http.post(`/spadmin/account/add`, form);
    }

}

export default new acceptUngLuongService();