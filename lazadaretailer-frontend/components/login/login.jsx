import React from 'react';
import LoginCSS from './login.css';
import Request from 'superagent';

class Login extends React.Component {

  constructor(props) {
    super(props);
    this.state = {error: ''};

    this.handleEmailChange = this.handleEmailChange.bind(this);
    this.handlePasswordChange = this.handlePasswordChange.bind(this);
    this.handleLogin = this.handleLogin.bind(this);
  }

  handleEmailChange(e) {
    this.setState({email: e.target.value});
  }

  handlePasswordChange(e) {
    this.setState({password: e.target.value});
  }

  handleLogin(event) {
    event.preventDefault();

    var self = this;
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
          self.setState({error: userResponse.error.message});
        } else if (userResponse.data != null) {
          alert(userResponse.data.name);
        } else {
          self.setState({error: "Unknown error !"});
        }
    });
  }

  render() {
    return (
      <div className="login-page">
        <div className="form">
          <form className="login-form" onSubmit={this.handleLogin}>
            <input type="text" placeholder="email" onChange={this.handleEmailChange} />
            <input type="password" placeholder="password" onChange={this.handlePasswordChange} />
            <button> Đăng Nhập </button>
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



