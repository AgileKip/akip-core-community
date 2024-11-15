package org.akip.delegate.executor;

public class AkipEmailConnectorMessageDTO {

    private String mailboxes;

    private String subject;

    private String content;

    public String getMailboxes() {
        return mailboxes;
    }

    public void setMailboxes(String mailboxes) {
        this.mailboxes = mailboxes;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return (
            "EmailActionMessage{" + "mailboxes='" + mailboxes + '\'' + ", subject='" + subject + '\'' + ", content='" + content + '\'' + '}'
        );
    }

    public String toMarkdown() {
        StringBuilder sb = new StringBuilder();
        sb.append("| MailBoxes | ");
        sb.append(this.mailboxes);
        sb.append(" |");
        sb.append("\n");
        sb.append("|-----------|----------------------------|");
        sb.append("\n");
        sb.append("| **Subject** | ");
        sb.append(this.subject);
        sb.append(" |");
        sb.append("\n<div class=\"card\">\n");
        sb.append("\n<div class=\"card-body\">\n");
        sb.append("\n");
        sb.append(this.content);
        sb.append("\n</div>");
        sb.append("\n</div>");
        return sb.toString();
    }
}
