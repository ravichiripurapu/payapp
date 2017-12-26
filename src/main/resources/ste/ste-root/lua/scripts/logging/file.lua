-------------------------------------------------------------------------------
-- $Id: file.lua,v 1.2 2006/02/20 18:07:49 tuler Exp $
--
-- Saves logging information in a file
--
-- Authors:
--   Thiago Costa Ponte (thiago@ideais.com.br)
--
-- Copyright (c) 2004-2006 Kepler Project
-- 	STE_VERSION "1.0.0.0"
-------------------------------------------------------------------------------

require"logging"

function logging.file(filename, datePattern, logPattern)

    if type(filename) ~= "string" then
        filename = "lualogging.log"
    end
    filename = string.format(filename, os.date(datePattern))

    return logging.new( function(self, level, message)
                            local f = io.open(filename, "a")
                            if not f then
                                return nil, string.format("file `%s' could not be opened for writing", filename)
                            end
                            local s = logging.prepareLogMsg(logPattern, os.date(), level, message)
                            f:write(s)
                            f:close()
                            
                            return true
                        end
                      )
end
