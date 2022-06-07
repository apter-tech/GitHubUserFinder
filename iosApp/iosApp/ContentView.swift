import SwiftUI

struct ContentView: View {
    var body: some View {
        NavigationView {
            GitHubUserListScreen()
                .navigationBarTitle(Text("GitHubUserFinder"))
        }
        .navigationViewStyle(StackNavigationViewStyle())
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
