// import * as request from '~/utils/request';
// export const login =async (q, type ='less')=>{
//     try {
//         const res= await request.get('/security/login',{
//             params:{
//                 q,
//                 type,
//             },
//         });
//         return res.data;
//     } catch (error) {
//         console.log(error);
        
//     }
// }

// axiosConfig.js

import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8081', // Thay thế bằng URL của API của bạn
  timeout: 5000, // Thời gian timeout, nếu không, mặc định là 0, tức là không timeout
  headers: {
    'Content-Type': 'application/json',
  },
});

export default axiosInstance;
