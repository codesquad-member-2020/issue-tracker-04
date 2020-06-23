import UIKit

extension UIViewController {
    /// Child ViewController 추가
    /// - parameter child: 붙이고 싶은 ViewController
    /// - parameter inView: 이 view에 `child.view`를 붙인다. `nil`일 경우 `self.view`가 된다.
    func add(_ child: UIViewController, inView parentView: UIView?) {
        let view: UIView = parentView ?? self.view
        child.view.frame = view.bounds

        addChild(child)
        view.addSubview(child.view)
        child.didMove(toParent: self)
    }

    func remove() {
        guard parent != nil else { return }

        willMove(toParent: nil)
        view.removeFromSuperview()
        removeFromParent()
    }
}

extension UIViewController {
    /// 스토리보드로 구체 타입으로 ViewController 생성
    /// - parameter identifier: `nil`이면 타입 이름으로 지정
    static func instantiateFromStoryboard(identifier: String? = nil, storyboard: UIStoryboard = .init(name: Identifier.Storyboard.main, bundle: .main)) -> Self {
        let identifier = identifier ?? String(describing: self)
        let viewController = storyboard.instantiateViewController(identifier: identifier) { coder in
            Self.init(coder: coder)
        }

        return viewController
    }
}
