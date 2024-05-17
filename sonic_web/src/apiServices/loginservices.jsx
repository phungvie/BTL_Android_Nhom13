import * as request from '../utils/request';

export const login = async (username, password) => {
    try {
        const response = await request.post('/security/login', {
            username,
            password
        });
        return response;
    } catch (error) {
        //xử lí lỗi đăng nhập ở đây
        console.log(error);
    }

}

export const login_github = async (code) => {
    try {
        const response = await request.post('/security/login_github?code='+code);
        return response;
    } catch (error) {
        //xử lí lỗi đăng nhập ở đây
        console.log(error);
    }

}

