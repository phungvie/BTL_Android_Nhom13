import { useState } from "react";
import * as loginServices from "../../apiServices/loginservices";
import config from '../../config';
import "./login.css";
function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  // console.log({
  //   username,
  //   password
  // })

  const handleSubmit = () => {
 console.log(   loginServices
  .login(username, password));

  };
  
  const viet = () => {
      window.location.href = 'https://github.com/login/oauth/authorize?client_id='+config.clientId+'&redirect_uri='+config.redirectUri+'&scope='+config.scope;
  };

  return (
    <main className="m-3">
      <div className="col-5 m-auto rounded p-5 bg-black">
        <form action="" method="post" className="d-flex flex-column">
          <label htmlFor="username" className="form-label text-light">
            Tên đăng nhập
          </label>
          <input
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            id="username"
            type="text"
            placeholder="Tên đăng nhập"
            className="form-control mb-3"
          />

          <label htmlFor="password" className="form-label text-light">
            Mật khẩu
          </label>
          <input
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            id="password"
            type="password"
            placeholder="Mật khẩu"
            className="form-control mb-4"
          />

          <div className="form-check form-switch">
            <input
              className="form-check-input"
              type="checkbox"
              role="switch"
              id="flexSwitchCheckDefault"
            />
            <label
              className="form-check-label text-light "
              htmlFor="flexSwitchCheckDefault"
            >
              Ghi nhớ tên đăng nhập
            </label>
          </div>
          <input
            onClick={handleSubmit}
            type="button"
            value="Đăng nhập "
            className="btn-custom btn btn-primary col-6 align-self-center mt-4 btn-lg"
          />
        </form>

        <div className="d-flex flex-column p-lg-5 mx-lg-5">
          <button className=" btn-custom btn btn-outline-secondary m-3">
            <img
              src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/2023_Facebook_icon.svg/600px-2023_Facebook_icon.svg.png"
              alt=""
            />{" "}
            Đăng nhập bằng Facebook
          </button>
          <button className=" btn-custom btn btn-outline-secondary m-3">
            <img src="https://i.pinimg.com/originals/84/c7/00/84c7007eb6ac4374f77394d1e4954a96.png" />{" "}
            Đăng nhập bằng Google
          </button>
          <button className=" btn-custom btn btn-outline-secondary m-3" onClick={viet}>
            <img
              src="https://static-00.iconduck.com/assets.00/github-light-icon-2048x1998-m3c0rgap.png"
              alt=""
            />{" "}
            Đăng nhập bằng GitHub
          </button>
        </div>
      </div>
    </main>
  );
}
export default Login;
