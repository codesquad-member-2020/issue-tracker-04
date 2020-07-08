import UIKit
import AuthenticationServices

class LoginViewController: UIViewController {

    let scheme = "issue04"
    @IBOutlet weak var loginButton: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    @IBAction func loginButtonTapped(_ sender: UIButton) {
        let authURL = Endpoint.oAuthLogin.url
        let scheme = self.scheme
        let session = ASWebAuthenticationSession(url: authURL, callbackURLScheme: scheme)
        { callbackURL, error in
            guard error == nil, let callbackURL = callbackURL else { return }

            let queryItems = URLComponents(string: callbackURL.absoluteString)?.queryItems
            let token = queryItems?.filter({ $0.name == "token" }).first?.value
            UserDefaults.standard.set(token, forKey: "JWTToken")
            UserDefaults.standard.object(forKey: "JWTToken")

            if token != nil {
                self.loginButton.setTitle("🎉", for: .normal)
                self.dismiss(animated: true, completion: nil)

                let vc = self.storyboard?.instantiateViewController(identifier: "IssueListViewController") as! IssueListViewController
                vc.modalPresentationStyle = .fullScreen
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
