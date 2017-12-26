-------------------------------------------------------------------------------
-- $Id: logging.lua,v 1.8 2006/08/14 18:11:59 carregal Exp $
-- includes a new tostring function that handles tables recursively
--
-- Authors:
--   Danilo Tuler (tuler@ideais.com.br)
--   André Carregal (carregal@keplerproject.org)
--   Thiago Costa Ponte (thiago@ideais.com.br)
--
-- Copyright (c) 2004-2006 Kepler Project
-- 	STE_VERSION "1.0.0.2"
-------------------------------------------------------------------------------

local type, table, string, assert, _tostring, gErrno, set_errno, get_strerrno = type, table, string, assert, tostring, gErrno, set_errno, get_strerrno

module("logging",package.seeall)

-- Meta information
_COPYRIGHT = "Copyright (C) 2004-2006 Kepler Project"
_DESCRIPTION = "A simple API to use logging features in Lua"
_VERSION = "LuaLogging 1.1.2"

-- The DEBUG Level designates fine-grained instring.formational events that are most
-- useful to debug an application
DEBUG = "DEBUG"

-- The INFO level designates instring.formational messages that highlight the
-- progress of the application at coarse-grained level
INFO = "INFO"

-- The WARN level designates potentially harmful situations
WARN = "WARN"

-- The ERROR level designates error events that might still allow the
-- application to continue running
ERROR = "ERROR"

-- The FATAL level designates very severe error events that will presumably
-- lead the application to abort
FATAL = "FATAL"

-- The OFF level turns off logging
OFF = "OFF"

local LEVEL = {
	[DEBUG] = 1,
	[INFO]  = 2,
	[WARN]  = 3,
	[ERROR] = 4,
	[FATAL] = 5,
	[OFF]   = 6,
}


-------------------------------------------------------------------------------
-- Creates a new logger object
-------------------------------------------------------------------------------
function new(append)

	if type(append) ~= "function" then
		return nil, "Appender must be a function."
	end

	local logger = {}
	logger.level = OFF
	logger.active = false
	logger.append = append

	logger.setLevel = function (self, level)
		assert(LEVEL[level], string.format("undefined level `%s'", tostring(level)))
		self.level = level
		self.active = (level ~= OFF) and true or false
	end

	logger.getLevel = function (self)
		return self.level
	end

	logger.log = function (self, level, message)
		assert(LEVEL[level], string.format("undefined level `%s'", tostring(level)))
		local errorNo = string.match(message,"^(%d+)|")
		if errorNo ~= nil then
			gErrno = tonumber(errorNo)
			set_errno(gErrno)
		end

		if LEVEL[level] < LEVEL[self.level] then
			return
		end
		if type(message) ~= "string" then
		  message = tostring(message)
		end
		return logger:append(level, message)
	end
	
	logger.fdebug = function (self, fnc)
		local str = debug.getinfo(2,"n").name
		if fnc == nil then fnc = "fdebug" end
		if str == nil then str = fnc .. ": " else str = str .. "(): " end
		local a = 1
	    local separator = ""
	    local entry = ""
		while true do
			local n, v = debug.getlocal(2,a)
			if not n then break end
			entry =  n .. " = " .. tostring(v)
		    str = str..separator..entry
		    separator = ", "
			a = a + 1
		end
		if a==1 then str = str .. "none" end
		logger:log(DEBUG, str)
	end
	
	logger.dumpvars = function(self, ...)
		local numArgs = select('#', ...)
		if numArgs ~= 0 then
			local str = "dumpvars: "
		    local separator = ""
	    	local entry = ""
			for i = 1, numArgs do
				local v = select(i, ...)
				entry = tostring(v)
			    str = str..separator..entry
		    	separator = ", "
			end
			logger:log(DEBUG, str)
		end
	end

	logger.debug = function (logger, message) return logger:log(DEBUG, message) end
	logger.info  = function (logger, message) return logger:log(INFO,  message) end
	logger.warn  = function (logger, message) return logger:log(WARN,  message) end
	logger.error = function (logger, message) return logger:log(ERROR, message) end
	logger.fatal = function (logger, message) return logger:log(FATAL, message) end
	return logger
end


-------------------------------------------------------------------------------
-- Prepares the log message
-------------------------------------------------------------------------------
function prepareLogMsg(pattern, dt, level, message)

    local logMsg = pattern or "%date %level %message\n"
    message = string.gsub(message, "%%", "%%%%")
    logMsg = string.gsub(logMsg, "%%date", dt)
    logMsg = string.gsub(logMsg, "%%level", level)
    logMsg = string.gsub(logMsg, "%%message", message)
    return logMsg
end

function table.val_to_str ( v )
  if "string" == type( v ) then
    v = string.gsub( v, "\n", "\\n" )
    if string.match( string.gsub(v,"[^'\"]",""), '^"+$' ) then
      return "'" .. v .. "'"
    end
    return '"' .. string.gsub(v,'"', '\\"' ) .. '"'
  else
    return "table" == type( v ) and table.tostring( v ) or
      _tostring( v )
  end
end

function table.key_to_str ( k )
  if "string" == type( k ) and string.match( k, "^[_%a][_%a%d]*$" ) then
    return k
  else
    return "[" .. table.val_to_str( k ) .. "]"
  end
end

function table.tostring( tbl )
  local result, done = {}, {}
  for k, v in ipairs( tbl ) do
    table.insert( result, table.val_to_str( v ) )
    done[ k ] = true
  end
  for k, v in pairs( tbl ) do
    if not done[ k ] then
      table.insert( result,
        table.key_to_str( k ) .. "=" .. table.val_to_str( v ) )
    end
  end
  return "{" .. table.concat( result, "," ) .. "}"
end

-------------------------------------------------------------------------------
-- Converts a Lua value to a string
--
-- Converts Table fields in alphabetical order
-------------------------------------------------------------------------------
function tostring(value)
  local str = ''

  if (type(value) ~= 'table') then
    if (type(value) == 'string') then
      str = string.format("%q", value)
    else
      str = _tostring(value)
    end
  else
    str = table.tostring(value)
  end
  return str
end
