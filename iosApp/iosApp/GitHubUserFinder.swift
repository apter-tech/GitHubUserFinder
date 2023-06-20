import SwiftUI

@main
struct GitHubUserFinder: App {
    // Lazy so it doesn't try to initialize before startKoin() is called
    lazy var logger = KoinApplication.getLogger(class: GitHubUserFinder.self)
    @State private var safeAreaInsets: (top: CGFloat, bottom: CGFloat) = (0, 0)

    init() {
        KoinApplication.start()
        logger.v(message: { "App Started" })
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
