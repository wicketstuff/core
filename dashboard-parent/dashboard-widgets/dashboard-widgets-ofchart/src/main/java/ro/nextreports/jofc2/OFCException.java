/*
This file is part of jofc2.

jofc2 is free software: you can redistribute it and/or modify
it under the terms of the Lesser GNU General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

jofc2 is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

See <http://www.gnu.org/licenses/lgpl-3.0.txt>.
*/

package ro.nextreports.jofc2;

public class OFCException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public OFCException(Throwable t) {
        super(t);
    }
    
    public OFCException(String message) {
        super(message);
    }
    
    public OFCException(String message, Throwable t) {
        super(message, t);
    }
}
