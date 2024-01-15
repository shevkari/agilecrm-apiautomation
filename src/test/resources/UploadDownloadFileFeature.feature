Feature: Verify Upload and Download File operation

  @FileUpload
  Scenario: Verify File Upload
    Given I setup a request structure to upload file
    When I hit an api to upload file
    Then I verify file upload successfully in response

  @FileDownloadInputStream
  Scenario: Verify File Download
    Given I setup a request structure to Download file
    When I hit an api to Download file
    Then I verify file Downloaded successfully in response using InputStream

  @FileDownloadByteStream
  Scenario: Verify File Download
    Given I setup a request structure to Download file
    When I hit an api to Download file
    Then I verify file Downloaded successfully in response using ByteStream