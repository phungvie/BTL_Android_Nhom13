import { Link } from 'react-router-dom';
import "./headerLogin.css";
function HeaderLogin() {
  return (
    <header className="bg-black clearfix p-2">
      <Link to="/" className='float-start text-decoration-none'><h1>Sonic</h1></Link>

    </header>
  );
}
export default HeaderLogin;
