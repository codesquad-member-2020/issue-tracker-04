import UIKit

/// IB에서 외곽선 처리
extension UIView {
    @IBInspectable var borderWidth: CGFloat {
        get { layer.borderWidth }
        set { layer.borderWidth = newValue }
    }

    @IBInspectable var borderColor: UIColor? {
        get { UIColor(cgColor: layer.borderColor!) }
        set { layer.borderColor = newValue?.cgColor }
    }

    @IBInspectable var cornerRadius: CGFloat {
        get { layer.cornerRadius }
        set { layer.cornerRadius = newValue }
    }

}

extension UIView {
    /// xib로 UIView 생성
    /// - parameter nibName: `nil`일 경우 뷰와 같은 이름으로 생성
    static func loadFromNib(_ nibName: String? = nil) -> Self {
        let nibName = nibName ?? String(describing: self)
        let nib = UINib(nibName: nibName, bundle: .main)

        return nib.instantiate(withOwner: self, options: .none).first as! Self
    }

    func addSubviewAsSameSize(_ view: UIView) {
        view.frame = self.bounds
        self.addSubview(view)
    }
}
