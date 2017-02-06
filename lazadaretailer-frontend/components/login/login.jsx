import React from 'react';
import LoginCSS from './login.css';
import Request from 'superagent';

class Login extends React.Component {

  constructor() {
    super();
    this.state = { email: null };
  }

  handleEmailChange(e) {
    this.setState({email: e.target.value});
  }

  handlePasswordChange(e) {
    this.setState({password: e.target.value});
  }

  handleLogin() {
    var url = "http://localhost:3000/user/login";
    Request
      .post(url)
      .set('Content-Type', 'application/x-www-form-urlencoded')
      .send({ email: this.state.email })
      .send({ password: this.state.password })
      .end(function(err, res) {
        var userResponse = JSON.parse(JSON.stringify(res.body));
        console.log(userResponse);
        if (userResponse.error != null) {
          this.setState({error: userResponse.error.message});
        } else if (userResponse.data != null) {
          alert(userResponse.data.name);
        } else {
          this.setState({error: "Unknown error !"});
        }
     });
  }

  render() {
    return (
      <div className="login-page">
        <div className="form">
          <form className="login-form">
            <input type="text" placeholder="email" onChange={e => this.handleEmailChange(e)} />
            <input type="password" placeholder="password" onChange={e => this.handlePasswordChange(e)} />
            <button onClick={this.handleLogin}> Đăng Nhập </button>
            <p>Error: {this.state.error || ''}</p>
            <p className="message">Bạn không có tài khoản?</p>
            <p className="highlight">Hãy gọi cho chúng tôi 01286.84.83.84</p>
          </form>
        </div>
      </div>
    );
  }
}

export default Login;



