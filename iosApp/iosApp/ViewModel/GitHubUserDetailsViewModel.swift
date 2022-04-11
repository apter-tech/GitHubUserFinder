//
//  GitHubUserDetailsViewModel.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 10..
//  Copyright © 2022. orgName. All rights reserved.
//

import Foundation
import SwiftUI

import shared

@MainActor
class GitHubUserDetailsViewModel: ObservableObject {
    @Published public var userDetails: GitHubUserDetails?
    @Published public var isLoading = false
    let service = GitHubUserService()

    public func refreshUserDetails(with userName: String) async {
        self.isLoading = true

        do {
            try await service.refreshUserDetails(userName: userName)
        } catch {
            self.isLoading = false
        }

        service.getUserDetails().subscribe { data in
            guard let userDetails = data else { return }
            self.userDetails = userDetails
            self.isLoading = false
        }
    }
}
