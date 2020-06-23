import UIKit

class SectionTitleLabel: UILabel {
    static let fontSize: CGFloat = 30

    override func awakeFromNib() {
        super.awakeFromNib()

        configure()
    }

    private func configure() {
        self.font = .systemFont(ofSize: Self.fontSize)
    }
}
