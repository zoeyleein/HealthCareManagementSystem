package com.hcms.healthcaremanagementsystem.invoicing;

import java.io.PrintWriter;

/**
 * Utility class for sending an HTML response that includes a countdown timer and automatically redirects the user
 * to a specified URL after the countdown expires. Used for redundancy.
 */
public class RedirectWithCountdown {

    /**
     * Sends an HTML response with a countdown timer and redirects the user to a specified URL after the countdown.
     *
     * @param out           PrintWriter to write the HTML response.
     * @param title         The title of the HTML page.
     * @param message       The message to display to the user on the page.
     * @param redirectUrl   The URL to redirect to after the countdown expires.
     * @param countdownTime The countdown time in seconds before the redirect occurs.
     */
    public static void send(PrintWriter out, String title, String message, String redirectUrl, int countdownTime) {
        // Begin the HTML document
        out.println("<html>");
        
        // Head section of the HTML document
        out.println("<head>");
        
        // Set the title of the page
        out.println("<title>" + title + "</title>");
        
        // Meta tag for automatic redirection after countdownTime seconds
        out.println("<meta http-equiv='refresh' content='" + countdownTime + ";url=" + redirectUrl + "'>");
        
        // Close the head section
        out.println("</head>");
        
        // Body section of the HTML document
        out.println("<body>");
        
        // Display the message in a heading tag
        out.println("<h1>" + message + "</h1>");
        
        // Display the countdown timer with an initial value of countdownTime
        out.println("<p>You will be redirected in <span id='countdown'>" + countdownTime + "</span> seconds...</p>");
        
        // Add a script to dynamically update the countdown on the page
        out.println("<script>");
        
        // Initialize a JavaScript variable with the countdown time
        out.println("let countdown = " + countdownTime + ";");
        
        // Get the span element where the countdown value is displayed
        out.println("const countdownSpan = document.getElementById('countdown');");
        
        // Use setInterval to decrease the countdown value every second
        out.println("const interval = setInterval(() => {");
        out.println("    countdown -= 1;"); // Decrement the countdown value
        out.println("    countdownSpan.textContent = countdown;"); // Update the displayed countdown value
        out.println("    if (countdown === 0) clearInterval(interval);"); // Stop the interval when countdown reaches 0
        out.println("}, 1000);");
        
        // Close the script tag
        out.println("</script>");
        
        // Close the body section
        out.println("</body>");
        
        // Close the HTML document
        out.println("</html>");
    }
}

