import axios from 'axios';

const request = axios.create({
    baseURL: 'http://localhost:8081',
});
export const get = async (path, options={})=>{
    const response = await request.get(path,options);
    return response
}
export const post = async (path, options={})=>{
    const response = await request.post(path,options);
    return response
}
