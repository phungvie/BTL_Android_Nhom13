import axios from 'axios';
import config from '../config';


const request = axios.create({
    baseURL: config.baseURLGitHub,
    withCredentials: true
});
export const get = async (path, options={})=>{
    const response = await request.get(path,options);
    return response
}
export const post = async (path, options={})=>{
    const response = await request.post(path,options);
    return response
}
