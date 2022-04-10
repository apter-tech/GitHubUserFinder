//
//  GitHubUserListScreen.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 10..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI

struct GitHubUserListScreen: View {
    @StateObject private var viewModel = GitHubUserListViewModel()
    @State var searchQuery = ""

    var body: some View {
        if viewModel.isLoading {
            ProgressView()
        } else {
            VStack {
                SearchBar { query in
                    Task {
                        await viewModel.searchUser(userName: query)
                    }
                }
                List {
                    ForEach(viewModel.items, id: \.id) { item in
                        NavigationLink(destination: GitHubUserDetailsScreen(item: item)) {
                            GitHubUserRow(item: item)
                        }
                    }
                }
            }
        }
    }
}
