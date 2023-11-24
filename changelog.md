# Changelog

* 0.1.8 (WIP)
  * Including RecSys

* 0.1.7
  * Including ChatGPT components (camunda delegate and form assistant)
  * Including Akip Prompt Configuration entity
  * Including new types: AkipAttachmentsTaskInstanceField and AkipNotesTaskInstanceField
  * Including Temporary Process Instance Entity
  * Including support for attachment and notes in the start form
  * Improving error messages

* 0.1.6
  * Including Process Timeline Definition
  * Including Process Timeline
  * Adding type TextArea (to be used in dynamic forms)
  * Fixing Notifiers bug (when messages were not defined)

* 0.1.5 (released August 2023)
  * Including support for DMN
  * Including Process Instance Migration APIs
  * Including new notifiers: AkipNotifyAssigneeDelegate, AkipNotifyCandidateGroupsDelegate, AkipNotifyCandidateUsersDelegate
  * Updating dependency `search-framework-server-side` from 1.2.5 to *1.2.8*
  * Adjusting the `ProcessInstanceMapper`
  * Including Tenant into the default start process page #30
  * Bug fix: reading the values property in an enum field.
  * Bug fix: closing notes when a task is completed
  * Including support for redo a 'redoable' task

* 0.1.3
  * Fixing bug with candidate group that did not support expression


* 0.1.2
    * Fixing bug in the claim feature for guest users (anonymous tasks)
    * Fixing a bug in the AkipEmailConnector for multiple email recipients

* 0.1.1
    * Upgrading Search Framework

* 0.1.0
    * Initial version of the enterprise edition.
    * The community architecture was refactored to the enterprise edition.
