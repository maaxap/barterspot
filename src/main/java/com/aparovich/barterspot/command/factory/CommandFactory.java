package com.aparovich.barterspot.command.factory;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.impl.*;
import com.aparovich.barterspot.command.impl.navigation.*;
import com.aparovich.barterspot.model.util.RoleType;
import com.aparovich.barterspot.validator.Validator;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.EnumSet;
import java.util.HashMap;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Realization of the <b>"Factory Method"</b> pattern for {@link Command} {@code interface}.
 *
 * @author Maxim Aparovich
 */
public class CommandFactory {

    /**
     * Instance of {@link Logger}, used for logging errors, warns and etc.
     */
    private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);

    /**
     * Map, which defines allowed {@link RoleType} for each command.
     */
    private static final HashMap<CommandType, EnumSet<RoleType>> pages = new HashMap<>();

    static {
        pages.put(CommandType.GOTO_INDEX,           EnumSet.of(RoleType.ADMIN, RoleType.CLIENT, RoleType.GUEST));
        pages.put(CommandType.GOTO_MAIN,            EnumSet.of(RoleType.ADMIN, RoleType.CLIENT, RoleType.GUEST));
        pages.put(CommandType.GOTO_ABOUT_US,        EnumSet.of(RoleType.ADMIN, RoleType.CLIENT, RoleType.GUEST));
        pages.put(CommandType.GOTO_CONTACTS,        EnumSet.of(RoleType.ADMIN, RoleType.CLIENT, RoleType.GUEST));
        pages.put(CommandType.GOTO_RULES,           EnumSet.of(RoleType.ADMIN, RoleType.CLIENT, RoleType.GUEST));
        pages.put(CommandType.GOTO_REGISTRATION,    EnumSet.of(RoleType.ADMIN, RoleType.CLIENT, RoleType.GUEST));
        pages.put(CommandType.GOTO_ERROR_403,       EnumSet.of(RoleType.ADMIN, RoleType.CLIENT, RoleType.GUEST));
        pages.put(CommandType.GOTO_ERROR_404,       EnumSet.of(RoleType.ADMIN, RoleType.CLIENT, RoleType.GUEST));
        pages.put(CommandType.GOTO_ERROR_500,       EnumSet.of(RoleType.ADMIN, RoleType.CLIENT, RoleType.GUEST));
        pages.put(CommandType.GOTO_LOTS,            EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.GOTO_LOT,             EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.GOTO_ADD_LOT,         EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.GOTO_EDIT_LOT,        EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.GOTO_PROFILE,         EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.GOTO_SETTINGS,        EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.GOTO_EDIT_PROFILE,    EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.GOTO_USER,            EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.GOTO_BIDS,            EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.GOTO_PAYMENT,         EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.GOTO_SEND_EMAIL,      EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.GOTO_ADMIN,           EnumSet.of(RoleType.ADMIN));

        pages.put(CommandType.LOCALIZE,             EnumSet.of(RoleType.ADMIN, RoleType.CLIENT, RoleType.GUEST));
        pages.put(CommandType.LOGIN,                EnumSet.of(RoleType.ADMIN, RoleType.CLIENT, RoleType.GUEST));
        pages.put(CommandType.LOGOUT,               EnumSet.of(RoleType.ADMIN, RoleType.CLIENT, RoleType.GUEST));
        pages.put(CommandType.MULTIPART_REQUEST,    EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.UPDATE_SETTINGS,      EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.ADD_BID,              EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.DELETE_BID,           EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.CONFIRM_LOT_PURCHASE, EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.DENY_LOT_PURCHASE,    EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.SEND_EMAIL,           EnumSet.of(RoleType.ADMIN, RoleType.CLIENT));
        pages.put(CommandType.ADD_CATEGORY,         EnumSet.of(RoleType.ADMIN));
        pages.put(CommandType.DELETE_CATEGORY,      EnumSet.of(RoleType.ADMIN));
        pages.put(CommandType.DELETE_LOT,           EnumSet.of(RoleType.ADMIN));
        pages.put(CommandType.BLOCK_USER,           EnumSet.of(RoleType.ADMIN));
        pages.put(CommandType.UNBLOCK_USER,         EnumSet.of(RoleType.ADMIN));
        pages.put(CommandType.DELETE_USER,          EnumSet.of(RoleType.ADMIN));


    }

    /**
     * Enumeration of all commands names, includes field {@link CommandType#command},
     * which contains suitable {@link Command} object.
     */
    private enum CommandType {
        GOTO_INDEX{
            {
                this.command = new GotoIndexCommand();
            }
        },
        GOTO_MAIN{
            {
                this.command = new GotoMainCommand();
            }
        },
        GOTO_ABOUT_US{
            {
                this.command = new GotoAboutUsCommand();
            }
        },
        GOTO_CONTACTS{
            {
                this.command = new GotoContactsCommand();
            }
        },
        GOTO_RULES{
            {
                this.command = new GotoRulesCommand();
            }
        },
        GOTO_REGISTRATION{
            {
                this.command = new GotoRegistrationCommand();
            }
        },
        GOTO_ERROR_403{
            {
                this.command = new GotoError403Command();
            }
        },
        GOTO_ERROR_404{
            {
                this.command = new GotoError403Command();
            }
        },
        GOTO_ERROR_500{
            {
                this.command = new GotoError403Command();
            }
        },
        GOTO_LOTS {
            {
                this.command = new GotoLotsCommand();
            }
        },
        GOTO_LOT{
            {
                this.command = new GotoLotCommand();
            }
        },
        GOTO_ADD_LOT{
            {
                this.command = new GotoAddLotCommand();
            }
        },
        GOTO_EDIT_LOT{
            {
                this.command = new GotoEditLotCommand();
            }
        },
        GOTO_PROFILE{
            {
                this.command = new GotoProfileCommand();
            }
        },
        GOTO_SETTINGS{
            {
                this.command = new GotoSettingsCommand();
            }
        },
        GOTO_EDIT_PROFILE{
            {
                this.command = new GotoEditProfileCommand();
            }
        },
        GOTO_SEND_EMAIL{
            {
                this.command = new GotoSendEmailCommand();
            }
        },
        GOTO_USER{
            {
                this.command = new GotoUserCommand();
            }
        },
        GOTO_BIDS{
            {
                this.command = new GotoBidsCommand();
            }
        },
        GOTO_PAYMENT{
            {
                this.command = new GotoPaymentCommand();
            }
        },
        GOTO_ADMIN{
            {
                this.command = new GotoAdminCommand();
            }
        },

        LOCALIZE {
            {
                this.command = new LocalizeCommand();
            }
        },
        LOGIN {
            {
                this.command = new LoginCommand();
            }
        },
        LOGOUT {
            {
                this.command = new LogoutCommand();
            }
        },
        MULTIPART_REQUEST{
            {
                this.command = new MultipartRequestCommand();
            }
        },
        UPDATE_SETTINGS{
            {
                this.command = new UpdateSettingsCommand();
            }
        },
        ADD_BID{
            {
                this.command = new AddBidCommand();
            }
        },
        DELETE_BID{
            {
                this.command = new DeleteBidCommand();
            }
        },
        CONFIRM_LOT_PURCHASE{
            {
                this.command = new ConfirmLotPurchaseCommand();
            }
        },
        DENY_LOT_PURCHASE{
            {
                this.command = new DenyLotPurchaseCommand();
            }
        },
        SEND_EMAIL{
            {
                this.command = new SendEmailCommand();
            }
        },
        ADD_CATEGORY{
            {
                this.command = new AddCategoryCommand();
            }
        },
        DELETE_CATEGORY{
            {
                this.command = new DeleteCategoryCommand();
            }
        },
        DELETE_LOT {
            {
                this.command = new DeleteLotCommand();
            }
        },
        BLOCK_USER{
            {
                this.command = new BlockUserCommand();
            }
        },
        UNBLOCK_USER{
            {
                this.command = new UnblockUserCommand();
            }
        },
        DELETE_USER{
            {
                this.command = new DeleteUserCommand();
            }
        };

        protected Command command;

        public Command getCommand() {
            return this.command;
        }
    }

    /**
     * Defines and return {@link Command} according to parameter {@code 'command'}
     * in {@link HttpServletRequest}.
     *
     * @param   request from client.
     * @return  suitable {@link Command} if it exists;
     *          {@link GotoMainCommand} otherwise.
     *
     * @see HttpServletRequest
     * @see javax.servlet.http.HttpSession
     * @see ServletFileUpload
     * @see CommandType#ejectCommand(String, RoleType)
     * @see Command
     */
    public static Command defineCommand(HttpServletRequest request) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart) {
            return CommandType.MULTIPART_REQUEST.getCommand();
        }
        RoleType role = RoleType.valueOf(((String) request.getSession().getAttribute(ROLE)).toUpperCase());
        String name = request.getParameter(COMMAND);
        if(!Validator.isEnumContains(name, CommandType.values())) {
            LOGGER.log(Level.ERROR, "Requested command does not exist: " + name);
            return CommandType.GOTO_MAIN.getCommand();
        }
        return ejectCommand(name, role);
    }

    /**
     * Ejects suitable {@link Command} from {@link CommandType}.
     *
     * @param   name of the command.
     * @param   role of the current {@link com.aparovich.barterspot.model.bean.User}
     *               placed in {@link javax.servlet.http.HttpSession}.
     * @return  suitable {@link Command} if it exists;
     *          {@link GotoMainCommand} otherwise.
     *
     * @see Command
     * @see CommandType
     * @see com.aparovich.barterspot.model.bean.User
     */
    private static Command ejectCommand(String name, RoleType role) {
        name = name.trim().replace('-', '_').toUpperCase();
        CommandType type = CommandType.valueOf(name);
        if(pages.get(type).contains(role)) {
            return type.getCommand();
        } else {
            return CommandType.GOTO_MAIN.getCommand();
        }
    }
}
