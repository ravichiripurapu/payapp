--[[

	Symmetry Tax Engine
	
	Base script - everything starts here!
	This script should provide enough context so the remaining scripts may be loaded from the database
	Copyright 2005-2008, Symmetry Software
	
	STE_VERSION "1.0.0.2"
]]

require "luasql.sqlite"
require "logging.stefile"
require "lbase64"
require "lmd5"
require "lbit"
require "lrex"
require "lfs"

gLogger = logging.stefile( STE_LOG_FILE_DIRECTORY .. "ste-%s.log", "%Y-%m-%d")

gLogger:setLevel (STE_LOGGING_LEVEL)

function ste_log_error( err, severe, msg )
	if severe then
		gLogger:fatal(tostring(err) .. "|" .. msg)
	else
		gLogger:info(msg)
	end
end

if string.lower(STE_DB_TYPE) == "mysql" then
	gEnvSQL = assert (luasql.mysql())
	gConSQL = assert ( gEnvSQL:connect( STE_DB_NAME, STE_DB_USERNAME, STE_DB_PASSWORD, STE_DB_HOST, STE_DB_PORT ) )
else
	gEnvSQL = assert (luasql.sqlite())
	gConSQL = assert ( gEnvSQL:connect(STE_DB_PATH) )
end

local functionCache = {}
local chman
--setmetatable(functionCache, {__mode = "kv"})  -- make values weak

function loadTaxID( taxID )
	local key = taxID .. gPayrollRunParameters.PayDate
	if functionCache[key] then return functionCache[key]
	else
		local cursor = assert (gConSQL:execute("SELECT taxCode from tax_code where taxID = '" .. taxID .. "' AND '" .. gPayrollRunParameters.PayDate .. "' >= tax_code.taxEffectiveDate ORDER BY tax_code.taxEffectiveDate DESC LIMIT 1") )
		local row = cursor:fetch ({}, "a")
		if row then
		  if ( row.taxCode ~= nil) then
		  	local script = ""
		  	if STE_DB_BASE64 == true then
		  		script = base64.decode(row.taxCode)
		  	else
		  		script = row.taxCode
		  	end
			assert(loadstring(script))()
			functionCache[key] = _G[ taxID ]
		  else
			gLogger:error("30|" .. tostring(taxID))
		  end
		else
			gLogger:error("30|" .. tostring(taxID))
		end
		cursor:close();
	end
	return functionCache[key]
end

local cursor = assert (gConSQL:execute( "SELECT * from scripts order by load_order" ))
local row = cursor:fetch ({}, "a")
while(row) do
	if ( row.script_code ~= nil) then
		local script = ""
		if STE_DB_BASE64 == true then
			script = base64.decode(row.script_code)
		else
			script = row.script_code
		end
		local s = assert(loadstring(script))()
		if row.script_name == "cacheManager" then chman = s end
	end
	row = cursor:fetch (row, "a")
end
cursor:close()

local functionCacheMan = {}
--setmetatable(functionCacheMan, {__mode = "kv"})  -- make values weak
local cacheMan = chman.newCacheManager()

function loadTaxIDMan( taxID )
	local retf
	local key = taxID .. gPayrollRunParameters.PayDate
	if functionCacheMan[key] then return functionCacheMan[key]
	else
		cursor = assert (gConSQL:execute("SELECT taxCode from tax_code where taxID = '" .. taxID .. "' AND '" .. gPayrollRunParameters.PayDate .. "' >= tax_code.taxEffectiveDate ORDER BY tax_code.taxEffectiveDate DESC LIMIT 1") )
		local row = cursor:fetch ({}, "a")
		if row then
		  if ( row.taxCode ~= nil) then
		  	local script = ""
		  	if STE_DB_BASE64 == true then
		  		script = base64.decode(row.taxCode)
		  	else
		  		script = row.taxCode
		  	end
			retf = assert(loadstring(script))()
			cacheMan.addFunction(retf)
			functionCacheMan[key] = retf
		  end
		end
		cursor:close();
	end
	return functionCacheMan[key]
end
