# Introduction

- Versioning: Proper Git usage with messages confirming to: https://cbea.ms/git-commit/
- Code style:
    - Extensive use of lambda and they are considered a type (like String and Int).
    - Mostly using expression body, since it shows clearly that the function is a 'pure' function
    - Using extension functions, since they are pure functions (but that with a parameter that is '
      this')
- Naming: By Kotlin convention
- Testing:
- Failures: https://www.morling.dev/blog/whats-in-a-good-error-message/

# Code Style

- By Kotlin convention and techniques
- Expression body
- Interface Oriented Programming (IOP vs. OOP)

# Backlog

| TODO | DOING | DONE |
| ---- | ----- | ---- |
| | | Discover the Rijksmuseum API: https://runkit.com/lamartio/621f1d2429367b00081238a4 (I will delete this a week after delivering)
| | | Setup a networking module
| | | Add a basic request
| | | Write an integration test
| | | Improve the returning value
| | | Get the details from Rijksmuseum
| | | Add a services module
| | | Add datastore
| | | Add a getter and setter for data
| | | Add a domain module for sharing unambiguous entities
| | | Persist something serializable
| | | Persist the collection and details
| | | Create a logic module
| Write a state composable mutation util based on Arrow-KT.
| Create a test suite that explains it's working
| Clarify the domain
| Clarify about Dependencies
| Clarify the network package
| URL in build config
| Centralize dependencies in BuildSrc (or Composite build)
| Add query to the collections API and UI
| See what I can do to log networking issues