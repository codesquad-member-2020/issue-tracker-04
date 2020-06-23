import UIKit

extension UIViewController {
    func add(_ child: UIViewController) {
        addChild(child)
//        child.view.translatesAutoresizingMaskIntoConstraints = false
        child.view.frame = view.bounds
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
