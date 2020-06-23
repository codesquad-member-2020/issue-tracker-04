import UIKit
import AuthenticationServices


enum EndPoints {
    static let oauthLogin = "https://github.com/login/oauth/authorize?client_id=bdd909bfff2137535182&redirect_uri=http://15.165.66.150/api/callback&scope=user"
}
class LoginViewController: UIViewController {

    let scheme = "issue04"
    @IBOutlet weak var loginButton: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()
    }
     
    @IBAction func loginButtonTapped(_ sender: UIButton) {
        guard let authURL = URL(string: EndPoints.oauthLogin) else { return }
        let scheme = self.scheme
        let session = ASWebAuthenticationSession(url: authURL, callbackURLScheme: scheme)
        { callbackURL, error in
            guard error == nil, let callbackURL = callbackURL else { return }

            let queryItems = URLComponents(string: callbackURL.absoluteString)?.queryItems
            let token = queryItems?.filter({ $0.name == "token" }).first?.value
            UserDefaults.standard.set(token, forKey: "JWTToken")
            UserDefaults.standard.object(forKey: "JWTToken")

            if token != nil {
                self.loginButton.setTitle("Login Success 🎉", for: .normal)
                self.dismiss(animated: true, completion: nil)
                let vc = self.storyboard?.instantiateViewController(identifier: "IssueListViewController") as! IssueListViewController
                self.present(vc, animated: true, completion: nil)
            }
        }
        session.presentationContextProvider = self
        session.start()
    }
}
extension LoginViewController: ASWebAuthenticationPresentationContextProviding {
    func presentationAnchor(for session: ASWebAuthenticationSession) -> ASPresentationAnchor {
        return view.window!
    }
}
