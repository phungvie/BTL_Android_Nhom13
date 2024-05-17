import { Link } from 'react-router-dom';
import "./header.css";
function Header() {
  return (
    <header className="bg-black clearfix">
      <Link to="/" className='float-start'><h1>Sonic</h1></Link>
      <Link to="/login" className='float-end'><h1>Đăng nhập</h1></Link>
    </header>
  );
}
export default Header;
