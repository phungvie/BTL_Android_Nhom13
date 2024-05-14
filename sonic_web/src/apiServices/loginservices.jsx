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

