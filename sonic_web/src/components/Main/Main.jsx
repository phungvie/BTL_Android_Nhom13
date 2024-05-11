import './Main.css';
function Main() {
  return (
    <main>
      <h1 className="p-4">viet</h1>
      <button className="px-4 btn-custom btn btn-outline-secondary m-3"><img src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/2023_Facebook_icon.svg/600px-2023_Facebook_icon.svg.png" alt="" /> Đăng nhập bằng Facebook</button>
      <button className="px-4 btn-custom btn btn-outline-secondary m-3"><i></i>Đăng nhập bằng Google</button>
      <button className="px-4 btn-custom btn btn-outline-secondary m-3"><i></i>Đăng nhập bằng GitHub</button>
    </main>
  );
}
export default Main;
